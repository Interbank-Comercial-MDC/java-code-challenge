package org.antifraude.application;

import org.antifraude.ports.in.FinancialTransactionUseCase;
import org.antifraude.ports.out.LoadFinancialTransactionPort;
import org.antifraude.domain.dto.FinancialTransactionDTO;
import org.antifraude.domain.model.FinancialTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class FinancialTransactionService implements FinancialTransactionUseCase {

    private final LoadFinancialTransactionPort loadFinancialTransactionPort;

    public FinancialTransactionService(LoadFinancialTransactionPort loadFinancialTransactionPort) {
        this.loadFinancialTransactionPort = loadFinancialTransactionPort;
    }

    @Override
    public Mono<FinancialTransaction> createFinancialTransaction(FinancialTransaction financialTransaction) {
        return loadFinancialTransactionPort.save(financialTransaction);
    }

    @Override
    public Mono<FinancialTransactionDTO> findFinancialTransactionByID(Long id) {
        return loadFinancialTransactionPort.findByID(id);
    }

    @Override
    public Mono<Void> updateFinancialTransactionByID(Long id) {
        return loadFinancialTransactionPort.update(id);
    }


}
