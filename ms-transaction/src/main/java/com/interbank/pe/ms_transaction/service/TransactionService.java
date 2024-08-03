package com.interbank.pe.ms_transaction.service;

import com.interbank.pe.ms_transaction.models.PatchRequest;
import com.interbank.pe.ms_transaction.models.TransactionRequest;
import com.interbank.pe.ms_transaction.models.TransactionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<Void> transaction(TransactionRequest request);
    Mono<Void> patchTransaction(Integer transactionId, PatchRequest request);
    Flux<TransactionResponse> getTransactions();
    Mono<TransactionResponse> getTransactionById(Integer transactionId);

}
