package com.challenge.transaction.service.impl;

import com.challenge.transaction.consumer.dto.TransactionDTO;
import com.challenge.transaction.consumer.dto.TransactionStateDTO;
import com.challenge.transaction.service.IAntiFraudService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AntiFraudService implements IAntiFraudService {

  @Value("${kafka.topic.transactions-state}")
  private String transactionStateTopic;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Override
  public void evaluateTransaction(TransactionDTO transactionDTO) {

    TransactionStateDTO transactionStateDTO =
        TransactionStateDTO.builder()
            .transactionId(transactionDTO.getTransactionId())
            .transactionStatusId(1)
            .build();
    if (transactionDTO.getValue().compareTo(BigDecimal.valueOf(1000.00)) > 0) {
      transactionStateDTO = transactionStateDTO.withTransactionStatusId(2);
    }
    kafkaTemplate.send(transactionStateTopic, transactionStateDTO);
  }
}
