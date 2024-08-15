package org.antifraude.ports.out;

import org.antifraude.domain.dto.FinancialTransactionDTO;
import org.antifraude.domain.model.FinancialTransaction;
import reactor.core.publisher.Mono;

public interface LoadFinancialTransactionPort {
    Mono<FinancialTransaction> save(FinancialTransaction financialTransaction);

    Mono<FinancialTransactionDTO> findByID(Long id);

    Mono<Void> update(Long id);
}
