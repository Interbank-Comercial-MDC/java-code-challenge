package com.interbank.challenge.service;

import com.interbank.challenge.entity.TransactionDocument;
import com.interbank.challenge.entity.dto.request.TransactionRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<TransactionRequest> createTransaction(TransactionRequest transaction) ;
    void updateTransactionStatus(ConsumerRecord<String, String> record);
    Mono<TransactionDocument> getTransactionById(String transactionId);

}
