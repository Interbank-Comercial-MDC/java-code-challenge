package com.interbank.challenge.service;

import com.interbank.challenge.entity.TransactionDocument;
import com.interbank.challenge.entity.dto.request.TransactionRequest;
import com.interbank.challenge.entity.dto.response.TransactionResponse;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<TransactionRequest> createTransaction(TransactionRequest transaction) ;
    void updateTransactionStatus(ConsumerRecord<String, String> record);
    Mono<TransactionResponse> getTransactionById(String transactionId);

}
