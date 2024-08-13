package com.hexagonal.demoAntiFraud.domain.ports.in;

import com.hexagonal.demoAntiFraud.infrastructure.entity.dto.TransactionDTO;
import com.hexagonal.demoAntiFraud.domain.models.RequestTransaction;
import reactor.core.publisher.Mono;

public interface TransactionUseCase {
    Mono<TransactionDTO> findTransactionByID(Long id);
    Mono<RequestTransaction> createTransaction(RequestTransaction transaction);
    Mono<Void> updateTransactionByID(Long id);
}