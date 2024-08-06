package com.interbank.challenge.service;

import com.interbank.challenge.entity.TransactionDocument;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<TransactionDocument> createTransaction(TransactionDocument transaction) ;
    Mono<Void> updateTransactionStatus(String transactionId, String status);
    Mono<TransactionDocument> getTransactionById(String transactionId);

}
