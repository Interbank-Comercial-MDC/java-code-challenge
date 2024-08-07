package com.interbank.challenge.service;

import com.interbank.challenge.entity.TransactionDocument;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<TransactionDocument> createTransaction(TransactionDocument transaction) ;
    void updateTransactionStatus(ConsumerRecord<String, String> record);
    Mono<TransactionDocument> getTransactionById(String transactionId);

}
