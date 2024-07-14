package com.challenge.transaction.consumer;

import com.challenge.transaction.consumer.dto.TransactionDTO;
import com.challenge.transaction.service.impl.AntiFraudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AntiFraudConsumer {

  private final AntiFraudService antiFraudService;

  @KafkaListener(topics = "${kafka.topic.transactions}", groupId = "${kafka.group-id}")
  public void getMessage(
      TransactionDTO transactionDTO, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
    log.info("Message received from topic {}: {}", topic, transactionDTO);
    antiFraudService.evaluateTransaction(transactionDTO);
  }
}
