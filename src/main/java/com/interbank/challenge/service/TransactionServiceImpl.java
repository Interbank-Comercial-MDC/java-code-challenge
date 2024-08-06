package com.interbank.challenge.service;

import com.interbank.challenge.entity.TransactionDocument;
import com.interbank.challenge.reporitory.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    private final String TOPIC = "registro-events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public Mono<TransactionDocument> createTransaction(TransactionDocument transaction) {
        transaction.setTransactionStatus("pendiente");
        transaction.setCreatedAt(LocalDate.now());

        System.out.println("preparando para enviar mensaje kafka");
        kafkaTemplate.send(TOPIC, "registro de operacion");
        System.out.println("fin mensaje kafka");

        return transactionRepository.save(transaction);


    }

    public Mono<Void> updateTransactionStatus(String transactionId, String status) {
        return transactionRepository.findById(transactionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Transaction not found")))
                .flatMap(transaction -> {
                    transaction.setTransactionStatus(status);
                    return transactionRepository.save(transaction)
                            .then(Mono.fromRunnable(() -> {
                                kafkaTemplate.send(TOPIC, "Transaction status updated: " + transactionId + " to " + status);
                            }));
                })
                .then();
    }

    public Mono<TransactionDocument> getTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Lista Vacia")));
    }
}
