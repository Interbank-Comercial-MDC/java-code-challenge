package com.hexagonal.demoAntiFraud.domain.ports.out;

import com.hexagonal.demoAntiFraud.infrastructure.entity.dto.TransactionDTO;
import com.hexagonal.demoAntiFraud.domain.models.RequestTransaction;
import reactor.core.publisher.Mono;

public interface TransactionPort {
    Mono<RequestTransaction> createTransaction(RequestTransaction Transaction);
    Mono<TransactionDTO> findByID(Long id);
    Mono<Void> updateTransactionStatus(Long id);
}