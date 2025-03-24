package pe.interbank.service.impl;

import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pe.interbank.model.DTO.TransactionRequest;
import pe.interbank.model.Transaction;
import pe.interbank.repository.TransactionRepository;
import pe.interbank.service.TransactionService;
import pe.interbank.util.JsonUtils;
import pe.interbank.util.StatusTransaction;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static pe.interbank.util.Contants.TOPIC_TRANSACTION_CREATED;

@Service
public class TransactionServiceImpl implements TransactionService {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TransactionServiceImpl.class);

  private final TransactionRepository transactionRepository;

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final JsonUtils jsonUtils;

  public TransactionServiceImpl(TransactionRepository transactionRepository, KafkaTemplate<String, String> kafkaTemplate, JsonUtils jsonUtils) {
    this.transactionRepository = transactionRepository;
    this.kafkaTemplate = kafkaTemplate;
    this.jsonUtils = jsonUtils;
  }

  @Override
  public Mono<Transaction> getTransaction(String accountExternalId) {
    Optional<Transaction> transaction = transactionRepository.findById(accountExternalId);

    if (!transaction.isPresent()) {
      logger.error("Transaction not found: {}", accountExternalId);
      return Mono.empty();
    }

    return Mono.just(transaction.get());
  }

  @Override
  public Mono<Transaction> sendTransaction(TransactionRequest transactionRq) {
    Transaction transaction = Transaction.builder()
        .accountExternalId(UUID.randomUUID().toString())
        .tranferTypeId(transactionRq.getTranferTypeId())
        .value(transactionRq.getValue())
        .status(StatusTransaction.PENDIENTE.getDescription())
        .createdAt(new Date())
        .build();

    Transaction transactionSaved = transactionRepository.save(transaction);
    logger.info("Transaction created: {}", transactionSaved);

    String transactionJson = jsonUtils.toJson(transactionSaved);
    kafkaTemplate.send(TOPIC_TRANSACTION_CREATED, transactionJson);
    logger.info("Transaction PENDIENTE sent to kafka: {}", transactionJson);

    return Mono.just(transactionSaved);
  }

  @Override
  public Mono<Void> updateTransactionStatus(Transaction transactionRq) {

    Optional<Transaction> transaction = transactionRepository.findById(transactionRq.getAccountExternalId());

    if (transaction.isPresent()) {
      Transaction transactionToUpdate = transaction.get();
      transactionToUpdate.setStatus(transactionRq.getStatus());
      transactionToUpdate.setUpdatedAt(new Date());
      transactionRepository.save(transactionToUpdate);
      logger.info("Transaction updated: {}", transactionToUpdate);
    }

    return Mono.empty();
  }

}
