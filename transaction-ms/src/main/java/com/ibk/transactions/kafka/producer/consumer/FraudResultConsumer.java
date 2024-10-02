package com.ibk.transactions.kafka.producer.consumer;

import com.ibk.transactions.entity.Transaction;
import com.ibk.transactions.entity.TransactionStatus;
import com.ibk.transactions.exceptions.TransactionNotFoundException;
import com.ibk.transactions.repository.TransactionRepository;
import com.ibk.transactions.repository.TransactionStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FraudResultConsumer {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionStatusRepository transactionStatusRepository;

    @KafkaListener(topics = "transactions.fraud-check.result", groupId = "transaction-service")
    public void consumeFraudResult(String message) {
        // MESSAGE LOOKS LIKE THIS "123123131231:APROBADO"
        String[] parts = message.split(":");
        Long transactionId = Long.parseLong(parts[0]);
        String fraudStatus = parts[1];

        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);

        if (transactionOptional.isPresent()) {
            Transaction transaction = transactionOptional.get();

            TransactionStatus newStatus;
            if (fraudStatus.equals("APROBADO")) {
                newStatus = transactionStatusRepository.findByName("APROBADO");
            } else if (fraudStatus.equals("RECHAZADO")) {
                newStatus = transactionStatusRepository.findByName("RECHAZADO");
            } else {
                throw new IllegalArgumentException("Estado de fraude desconocido: " + fraudStatus);
            }

            transaction.setTransactionStatus(newStatus);
            transactionRepository.save(transaction);
        } else {
            throw new TransactionNotFoundException(transactionId);
        }
    }
}