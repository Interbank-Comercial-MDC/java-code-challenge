package org.antifraude.ports.in;

import org.antifraude.domain.dto.FinancialTransactionDTO;
import org.antifraude.domain.model.FinancialTransaction;
import reactor.core.publisher.Mono;

public interface FinancialTransactionUseCase {
    Mono<FinancialTransaction> createFinancialTransaction(FinancialTransaction financialTransaction);

    Mono<FinancialTransactionDTO> findFinancialTransactionByID(Long id);

    Mono<Void> updateFinancialTransactionByID(Long id);
}
