package com.hexagonal.demoAntiFraud.infrastructure.adapters;

import com.hexagonal.demoAntiFraud.domain.models.TransferStatus;
import com.hexagonal.demoAntiFraud.domain.ports.out.TransactionPort;
import com.hexagonal.demoAntiFraud.infrastructure.entity.dto.TransactionDTO;
import com.hexagonal.demoAntiFraud.domain.models.RequestTransaction;
import com.hexagonal.demoAntiFraud.domain.models.TransferType;
import com.hexagonal.demoAntiFraud.infrastructure.mapper.TransactionMapper;
import com.hexagonal.demoAntiFraud.infrastructure.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.hexagonal.demoAntiFraud.infrastructure.util.Constants.VALUE_LIMIT;

@Slf4j
@Component
public class TransactionAdapter implements TransactionPort {

    private final TransactionRepository transactionRepository;

    public TransactionAdapter(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Mono<RequestTransaction> createTransaction(RequestTransaction payloadTransaction) {
        RequestTransaction transaction = TransactionMapper.TransactionRequestMapperToTransaction(payloadTransaction);
        transaction.setTransactionExternalId(UUID.randomUUID());
        return transactionRepository.save(transaction);

    }

    @Override
    public Mono<TransactionDTO> findByID(Long id) {
        return transactionRepository.findById(id)
                .map(ft -> TransactionDTO.builder()
                        .transactionExternalId(ft.getTransactionExternalId())
                        .value(ft.getValue())
                        .createdAt(ft.getCreatedAt())
                        .transactionType(TransactionDTO.TransactionType.of(
                                TransferType.getValueByID(ft.getTransferTypeId())))
                        .transactionStatus(TransactionDTO.TransactionStatus.of(TransferStatus.getValueByID(ft.getTransactionStatusId())))
                        .build());
    }

    @Override
    public Mono<Void> updateTransactionStatus(Long id) {
        return transactionRepository.findById(id).flatMap(transaction -> {
            transaction.setTransactionStatusId(transaction.getValue() > VALUE_LIMIT ? TransferStatus.REFUSED.getId() : TransferStatus.APPROVED.getId());
            return transactionRepository.save(transaction);
        }).then();
    }
}