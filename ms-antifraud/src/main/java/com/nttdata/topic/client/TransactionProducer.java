package com.nttdata.topic.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${kafka.topic.transactions.trx-rejected-producer}")
    private String trxRejectedProducer;

    @Value("${kafka.topic.transactions.trx-approved-producer}")
    private String trxApprovedProducer;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void transactionApprovedMessage(String transactionId) {
        try {
            kafkaTemplate.send(trxApprovedProducer, transactionId);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public void transaccionRejectedMessage(String transactionId) {
        try {
            kafkaTemplate.send(trxRejectedProducer, transactionId);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

    }
}
