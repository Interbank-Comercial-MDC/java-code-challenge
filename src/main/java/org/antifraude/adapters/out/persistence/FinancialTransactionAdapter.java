package org.antifraude.adapters.out.persistence;

import org.antifraude.ports.out.LoadFinancialTransactionPort;
import org.antifraude.domain.dto.FinancialTransactionDTO;
import org.antifraude.domain.model.FinancialTransaction;
import org.antifraude.domain.model.StatusTransfer;
import org.antifraude.domain.model.TransferType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class FinancialTransactionAdapter implements LoadFinancialTransactionPort {

    private final FinancialTransactionRepository financialTransactionRepository;

    public FinancialTransactionAdapter(FinancialTransactionRepository financialTransactionRepository) {
        this.financialTransactionRepository = financialTransactionRepository;
    }

    @Override
    public Mono<FinancialTransaction> save(FinancialTransaction financialTransaction) {
        financialTransaction.setTransactionExternalId(UUID.randomUUID());
        financialTransaction.setTransactionStatusId(StatusTransfer.PENDING.getId());
        return financialTransactionRepository.save(financialTransaction);
    }

    @Override
    public Mono<FinancialTransactionDTO> findByID(Long id) {
        return financialTransactionRepository.findById(id)
                .map(ft -> FinancialTransactionDTO.builder()
                        .transactionExternalId(ft.getTransactionExternalId())
                        .value(ft.getValue())
                        .createdAt(ft.getCreatedAt())
                        .transactionType(FinancialTransactionDTO.TransactionType.of(
                                TransferType.getValueByID(ft.getTransferTypeId())))
                        .transactionStatus(FinancialTransactionDTO.TransactionStatus
                                .of(StatusTransfer.getValueByID(ft.getTransactionStatusId())))
                        .build());


    }

    @Override
    public Mono<Void> update(Long id) {
        return financialTransactionRepository.findById(id).flatMap(financialTransaction -> {
            if (financialTransaction.getValue() > 1000) {
                financialTransaction.setTransactionStatusId(StatusTransfer.REFUSED.getId());
            } else {
                financialTransaction.setTransactionStatusId(StatusTransfer.APPROVED.getId());
            }
            return financialTransactionRepository.save(financialTransaction);
        }).then();
    }
}
