package com.ibk.transactions.kafka.producer;

import com.ibk.transactions.dto.CreateTransactionRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducer {

    private final KafkaTemplate<String, CreateTransactionRequestDTO> kafkaTemplate;
    private static final String TOPIC = "transactions.fraud-check";

    @Autowired
    public TransactionProducer(KafkaTemplate<String, CreateTransactionRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(CreateTransactionRequestDTO transactionRequest) {
        kafkaTemplate.send(TOPIC, transactionRequest);
    }
}
