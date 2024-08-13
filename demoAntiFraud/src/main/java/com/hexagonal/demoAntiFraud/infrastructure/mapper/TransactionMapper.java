package com.hexagonal.demoAntiFraud.infrastructure.mapper;

import com.hexagonal.demoAntiFraud.domain.models.RequestTransaction;
import com.hexagonal.demoAntiFraud.domain.models.TransferStatus;

import java.time.LocalDateTime;

public class TransactionMapper {
    public static RequestTransaction TransactionRequestMapperToTransaction(RequestTransaction request) {
        return RequestTransaction.builder()
                .accountExternalIdDebit(request.getAccountExternalIdDebit())
                .accountExternalIdCredit(request.getAccountExternalIdCredit())
                .transferTypeId(request.getTransferTypeId())
                .value(request.getValue())
                .transactionStatusId(TransferStatus.PENDING.getId())
                .createdAt(LocalDateTime.now()).build();
    }
}
