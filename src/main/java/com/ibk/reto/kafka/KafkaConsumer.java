package com.ibk.reto.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ibk.reto.model.Transaction;
import com.ibk.reto.model.TransactionEstatus;
import com.ibk.reto.service.TransactionService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Reto Tecnico IBK.
 *
 * @author Alvaro Cubas Huarca
 * @version 1.0
 * @since 2025-03-23
 */
@Service
public class KafkaConsumer {
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    public KafkaConsumer(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @KafkaListener(topics = "transaction_created", groupId = "antifraud-group")
    public void processTransaction(ConsumerRecord<String, String> record) {
        try {
            Transaction transaction = objectMapper.readValue(record.value(), Transaction.class);

            // Lógica antifraude: rechazar transacciones > 1000
            TransactionEstatus status = transaction.getValue() > 1000 ? TransactionEstatus.RECHAZADO : TransactionEstatus.APROBADO;

            // Enviar evento de actualización de estado a transaction service
            kafkaProducer.sendTransactionStatus(transaction.getTransactionExternalId(), status);

        } catch (Exception e) {
            throw new RuntimeException("Error processing Kafka message in AntiFraud Service", e);
        }
    }
}
