package com.interbank.pe.ms_transaction.service.impl;

import com.interbank.pe.ms_transaction.data.entities.Transaction;
import com.interbank.pe.ms_transaction.data.entities.TransactionStatus;
import com.interbank.pe.ms_transaction.data.entities.TransactionType;
import com.interbank.pe.ms_transaction.data.repositories.TransactionRepository;
import com.interbank.pe.ms_transaction.data.repositories.TransactionStatusRepository;
import com.interbank.pe.ms_transaction.data.repositories.TransactionTypeRepository;
import com.interbank.pe.ms_transaction.mapper.TransactionMapper;
import com.interbank.pe.ms_transaction.models.PatchRequest;
import com.interbank.pe.ms_transaction.models.TransactionRequest;
import com.interbank.pe.ms_transaction.models.TransactionResponse;
import com.interbank.pe.ms_transaction.models.event.TransactionEvent;
import com.interbank.pe.ms_transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final TransactionStatusRepository transactionStatusRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    @Override
    public Mono<Void> transaction(TransactionRequest request) {
        log.info("Saving new transaction");
        return transactionRepository.save(transactionMapper.buildTransaction(request, new Date()))
                .doOnSuccess(success -> log.info("Transaction created!"))
                .map(transaction -> kafkaTemplate.send("transactionTopic", new TransactionEvent(transaction.getId(), request.getValue())))
                .then();
    }

    @Override
    public Mono<Void> patchTransaction(Integer transactionId, PatchRequest request) {
        log.info("Updating transaction");
        return transactionRepository.findById(transactionId)
                .flatMap(transaction -> {
                    transaction.setStatusCode(request.getStatusCode());
                    return transactionRepository.save(transaction);
                })
                .then();
    }

    @Override
    public Flux<TransactionResponse> getTransactions() {
        log.info("Retrieve transactions");
        return transactionRepository.findAll()
                .flatMap(this::getTransaction);
    }

    @Override
    public Mono<TransactionResponse> getTransactionById(Integer transactionId) {
        log.info("Retrieve transaction by id");
        return transactionRepository.findById(transactionId)
                .flatMap(this::getTransaction);
    }

    private Mono<TransactionResponse> getTransaction(Transaction transaction) {
        return Mono.zip(getTransactionTypeName(transaction.getTypeCode()), getTransactionStatusName(transaction.getStatusCode()))
                .subscribeOn(Schedulers.parallel())
                .map(tuple -> transactionMapper.buildTransactionResponse(transaction,
                        tuple.getT1(), tuple.getT2()));
    }

    private Mono<String> getTransactionStatusName(Integer statusCode) {
        return transactionStatusRepository.findById(statusCode)
                .map(TransactionStatus::getName);
    }

    private Mono<String> getTransactionTypeName(Integer typeCode) {
        return transactionTypeRepository.findById(typeCode)
                .map(TransactionType::getName);
    }

}
