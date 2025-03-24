package com.ibk.reto.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ibk.reto.model.TransactionEstatus;
import com.ibk.reto.service.TransactionService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
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
public class TransactionKafkaConsumer {
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    public TransactionKafkaConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @KafkaListener(topics = "transaction_status_updated", groupId = "transaction-group")
    public void updateTransactionStatus(ConsumerRecord<String, String> record) {
        try {
            JsonNode jsonNode = objectMapper.readTree(record.value());
            UUID transactionId = UUID.fromString(jsonNode.get("transactionId").asText());
            TransactionEstatus status = TransactionEstatus.valueOf(jsonNode.get("status").asText());

            // Actualizar estado en la base de datos
            transactionService.updateTransactionStatus(transactionId, status);

        } catch (Exception e) {
            throw new RuntimeException("Error processing Kafka message in Transaction Service", e);
        }
    }
}
