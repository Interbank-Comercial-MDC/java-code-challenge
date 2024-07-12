package com.reto.interbank.ms_transaction.service.impl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reto.interbank.ms_transaction.dto.TransactionDTO;
import com.reto.interbank.ms_transaction.mapper.TransactionMapper;
import com.reto.interbank.ms_transaction.model.Transactions;
import com.reto.interbank.ms_transaction.repository.TransactionRepository;
import com.reto.interbank.ms_transaction.service.TransactionService;
import com.reto.interbank.ms_transaction.service.constans.ServiceConstans;

import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private KafkaSender<String, String> kafkaSender;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Mono<Transactions> createTransaction(Transactions transaction) {
        transaction.setStatus(ServiceConstans.STATUS_PENDIENTE);
        transaction.setCreatedAt(LocalDateTime.now());

        log.info(ServiceConstans.getTransactionLogMessage(transaction.getId()));

        return repository.save(transaction)
                .flatMap(savedTransaction -> {
                    log.info("Transacción guardada con ID: {}", savedTransaction.getId());
                    sendTransactionToAntiFraud(savedTransaction.getId(), savedTransaction.getValue());
                    return Mono.just(savedTransaction);
                });
    }

    public void sendTransactionToAntiFraud(UUID transactionId, Double value) {
        try {
            String message = objectMapper.writeValueAsString(Map.of(
                    "transactionId", transactionId.toString(),
                    "value", value.toString()
            ));

            log.info(ServiceConstans.getSendFraudLogMessage(message));

            kafkaSender.send(Mono.just(SenderRecord.create(
                    new ProducerRecord<>(ServiceConstans.TOPIC_SEND_FRAUD, message), null)))
                    .doOnError(error -> handleKafkaSendError(error, message))
                    .subscribe();
        } catch (JsonProcessingException e) {
            log.error("Error al convertir objeto a JSON para enviar a Kafka", e);
        }
    }
    
    private void handleKafkaSendError(Throwable error, String message) {
        log.error("Error al enviar mensaje a Kafka: {}", error.getMessage());
    }
    
    public Mono<TransactionDTO> getTransaction(UUID id) {
        return repository.findById(id)
                .map(TransactionMapper::toTransactionDTO);
    }
}