package com.ibk.antifraude.application.ports.in;

import com.ibk.antifraude.domain.dto.FinancialTransactionDTO;
import com.ibk.antifraude.domain.model.FinancialTransaction;
import reactor.core.publisher.Mono;

public interface FinancialTransactionUseCase {
    Mono<FinancialTransaction> createFinancialTransaction(FinancialTransaction financialTransaction);

    Mono<FinancialTransactionDTO> findFinancialTransactionByID(Long id);

    Mono<Void> updateFinancialTransactionByID(Long id);
}
