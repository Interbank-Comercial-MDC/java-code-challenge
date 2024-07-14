package com.challenge.transaction.service.impl;

import com.challenge.transaction.cache.TransactionCache;
import com.challenge.transaction.consumer.dto.TransactionDTO;
import com.challenge.transaction.model.Transaction;
import com.challenge.transaction.repository.TransactionRepository;
import com.challenge.transaction.repository.converter.TransactionMapper;
import com.challenge.transaction.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class TransactionService implements ITransactionService {

  private final TransactionCache transactionCache;
  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;

  @Value("${kafka.topic.transactions}")
  private String transactionTopic;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Override
  public Transaction getTransaction(Integer transactionId) {
    return transactionCache.getTransaction(transactionId);
  }

  @Override
  public Transaction createTransaction(Transaction transaction) {
    Transaction transactionResult =
        transactionMapper.toModelFromEntity(
            transactionRepository.save(transactionMapper.toEntityFromModel(transaction)));

    kafkaTemplate.send(
        transactionTopic,
        TransactionDTO.builder()
            .transactionId(transactionResult.getTransactionExternalId())
            .value(transactionResult.getValue())
            .build());

    return transactionResult;
  }

  @Override
  public void updateTransactionState(Transaction transaction) {
    Integer result =
        transactionRepository.updateTransactionStatusId(
            transaction.getTransactionExternalId(), transaction.getTransactionStatusId());
    if (result == 0) {
      log.error(
          "Failed in updateTransactionState with transactionId: {}",
          transaction.getTransactionExternalId());
      throw new RuntimeException("Failed to updateTransactionState");
    }
    transactionCache.deleteTransactionCache(transaction.getTransactionExternalId());
  }
}
