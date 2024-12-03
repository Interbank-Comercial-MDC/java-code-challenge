package com.prueba.crea.msgestionatransaction.service;

import com.prueba.crea.msgestionatransaction.model.Transaction;
import com.prueba.crea.msgestionatransaction.model.TransactionRequest;
import com.prueba.crea.msgestionatransaction.model.TransactionStatusUpdate;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<Transaction> sendTransaction(TransactionRequest tx);

    Mono<Void> updateTransaction(TransactionStatusUpdate txActualiza);
}
