package com.ibk.reto.service;

import com.ibk.reto.kafka.KafkaProducer;
import com.ibk.reto.model.Transaction;
import com.ibk.reto.model.TransactionEstatus;
import com.ibk.reto.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository repository;
    private final KafkaProducer kafkaProducer;

    public TransactionService(TransactionRepository repository, KafkaProducer kafkaProducer) {
        this.repository = repository;
        this.kafkaProducer = kafkaProducer;
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        transaction.setTransactionStatus(TransactionEstatus.PENDIENTE);
        Transaction savedTransaction = repository.save(transaction);
        kafkaProducer.sendTransaction(savedTransaction);
        return savedTransaction;
    }

    public Transaction getTransactionById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Transactional
    public void updateTransactionStatus(UUID transactionId, TransactionEstatus status) {
        Transaction transaction = getTransactionById(transactionId);
        transaction.setTransactionStatus(status);
        repository.save(transaction);
    }

}
