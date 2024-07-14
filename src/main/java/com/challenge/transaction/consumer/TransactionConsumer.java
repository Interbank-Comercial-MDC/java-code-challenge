package com.challenge.transaction.consumer;

import com.challenge.transaction.consumer.dto.TransactionDTO;
import com.challenge.transaction.consumer.dto.TransactionStateDTO;
import com.challenge.transaction.controller.converter.TransactionResponseMapper;
import com.challenge.transaction.controller.response.TransactionResponse;
import com.challenge.transaction.repository.converter.TransactionMapper;
import com.challenge.transaction.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionConsumer {
  private final ITransactionService transactionService;
  private final TransactionResponseMapper transactionResponseMapper;

  @KafkaListener(
      topics = "${kafka.topic.transactions-state}",
      groupId = "${kafka.group-id}",
      containerFactory = "transactionsStateListenerContainerFactory")
  public void getMessage(
      TransactionStateDTO transactionStateDTO, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
    log.info("Message received from topic {}: {}", topic, transactionStateDTO);
    transactionService.updateTransactionState(
        transactionResponseMapper.toTransactionFromTransactionStateDTO(transactionStateDTO));
  }
}
