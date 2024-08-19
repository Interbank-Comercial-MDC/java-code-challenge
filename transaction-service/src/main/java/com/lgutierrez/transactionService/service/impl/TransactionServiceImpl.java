package com.lgutierrez.transactionService.service.impl;

import com.lgutierrez.transactionService.dto.TransactionDTO;
import com.lgutierrez.transactionService.entity.Account;
import com.lgutierrez.transactionService.entity.Transaction;
import com.lgutierrez.transactionService.enums.TransactionStatus;
import com.lgutierrez.transactionService.enums.TransactionType;
import com.lgutierrez.transactionService.exception.ResourceNotFoundException;
import com.lgutierrez.transactionService.mapper.TransactionMapper;
import com.lgutierrez.transactionService.repository.AccountRepository;
import com.lgutierrez.transactionService.repository.TransactionRepository;
import com.lgutierrez.transactionService.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    @Autowired
    private WebClient webClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public TransactionDTO getTransactionById(UUID transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                                                        new ResourceNotFoundException("Transaction", "id", transactionId));
        return TransactionMapper.mapToTransactionDTO(transaction);
    }

    @Override
    public TransactionDTO registerTransaction(TransactionDTO transactionDTO) throws Exception {
        //get the account by Id
        UUID accountId = transactionDTO.accountExternalIdCredit() == null ? transactionDTO.accountExternalIdDebit() : null;
        if(accountId == null)
            throw new Exception("something went wrong");

        Account account = accountRepository.findById(accountId).orElseThrow(() ->
                                            new ResourceNotFoundException("Account", "id", accountId));

        LocalDateTime now = LocalDateTime.now();

        //save in DB
        Transaction transaction = new Transaction(null,
                                                  TransactionStatus.PENDING,
                                                  TransactionType.FAST,
                                                  transactionDTO.value(),
                                                  now,
                                                  now,
                                                  account);

        Transaction newTransaction = transactionRepository.save(transaction);

        //send to anti-fraud service
        String res = SendToAntifraudService(newTransaction);
        LOGGER.info(String.format("response message => %s", res));
        //to -> Dto object response
        return TransactionMapper.mapToTransactionDTO(newTransaction);
    }


    @KafkaListener(topics = "${spring.kafka.topic.name}",
                   groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Transaction transaction){
        LOGGER.info("update transaction: {}", transaction.toString());
        Transaction transaction1 = transactionRepository.findById(transaction.getId()).orElseThrow(() ->
                                                                  new ResourceNotFoundException("Transaction", "id", transaction.getId()));
        transaction1.setStatus(transaction.getStatus());
        Transaction trnx = transactionRepository.save(transaction1);
        LOGGER.info("New transaction saved in DB: {}", trnx);

    }


    private String SendToAntifraudService(Transaction transaction) {

        return webClient.post()
                .uri("http://localhost:8081/api/antifraud")
                .body(Mono.just(transaction), Transaction.class)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> {
                    // Handle successful response
                    LOGGER.debug("Success : {}", response);
                })
                .doOnError(error -> {
                    // Handle error response
                    LOGGER.error("ERROR : {}", error.getMessage());
                })
                .block();
    }
}
