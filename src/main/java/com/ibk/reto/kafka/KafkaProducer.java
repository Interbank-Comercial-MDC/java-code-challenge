package com.ibk.reto.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ibk.reto.model.Transaction;
import com.ibk.reto.model.TransactionEstatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Reto Tecnico IBK.
 *
 * @author Alvaro Cubas Huarca
 * @version 1.0
 * @since 2025-03-23
 */
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void sendTransaction(Transaction transaction) {
        try {
            String message = objectMapper.writeValueAsString(transaction);
            kafkaTemplate.send("transaction_created", message);
        } catch (Exception e) {
            throw new RuntimeException("Error sending Kafka message", e);
        }
    }

    public void sendTransactionStatus(UUID transactionId, TransactionEstatus status) {
        try {
            String message = objectMapper.writeValueAsString(new TransactionStatusUpdate(transactionId, status));
            kafkaTemplate.send("transaction_status_updated", message);
        } catch (Exception e) {
            throw new RuntimeException("Error sending Kafka message", e);
        }
    }

    private static class TransactionStatusUpdate {
        public UUID transactionId;
        public TransactionEstatus status;

        public TransactionStatusUpdate(UUID transactionId, TransactionEstatus status) {
            this.transactionId = transactionId;
            this.status = status;
        }
    }
}
