package com.ibk.antifraude.application.ports.out;

import com.ibk.antifraude.domain.dto.FinancialTransactionDTO;
import com.ibk.antifraude.domain.model.FinancialTransaction;
import reactor.core.publisher.Mono;

public interface LoadFinancialTransactionPort {
    Mono<FinancialTransaction> save(FinancialTransaction financialTransaction);

    Mono<FinancialTransactionDTO> findByID(Long id);

    Mono<Void> update(Long id);
}
