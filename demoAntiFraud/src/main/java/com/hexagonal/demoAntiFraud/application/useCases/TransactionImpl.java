package com.hexagonal.demoAntiFraud.application.useCases;

import com.hexagonal.demoAntiFraud.domain.ports.in.TransactionUseCase;
import com.hexagonal.demoAntiFraud.domain.ports.out.TransactionPort;
import com.hexagonal.demoAntiFraud.infrastructure.entity.dto.TransactionDTO;
import com.hexagonal.demoAntiFraud.domain.models.RequestTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class TransactionImpl implements TransactionUseCase {

    private final TransactionPort transactionPort;

    public TransactionImpl(TransactionPort transactionPort) {
        this.transactionPort = transactionPort;
    }

    @Override
    public Mono<RequestTransaction> createTransaction(RequestTransaction transaction) {
        return transactionPort.createTransaction(transaction);
    }

    @Override
    public Mono<TransactionDTO> findTransactionByID(Long id) {
        return transactionPort.findByID(id);
    }

    @Override
    public Mono<Void> updateTransactionByID(Long id) {
        return transactionPort.updateTransactionStatus(id);
    }

}