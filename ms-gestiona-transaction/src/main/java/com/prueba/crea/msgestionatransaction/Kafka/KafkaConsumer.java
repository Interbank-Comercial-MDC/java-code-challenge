package com.prueba.crea.msgestionatransaction.Kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.crea.msgestionatransaction.model.TransactionStatusUpdate;
import com.prueba.crea.msgestionatransaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

@Configuration
public class KafkaConsumer {

    @Autowired
    TransactionService txService;

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "topic-valida-transaction", groupId = "my-group-id")
    public void consumerValidateTransaction(String message) throws IOException {
        logger.info("Transaction VALIDADA: " + message);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            TransactionStatusUpdate transaction = objectMapper.readValue(message, TransactionStatusUpdate.class);
            txService.updateTransaction(transaction);

        } catch (Exception e) {
            logger.error("Error al convertir JSON a Entity: " + e.getMessage());
        }
    }
}
