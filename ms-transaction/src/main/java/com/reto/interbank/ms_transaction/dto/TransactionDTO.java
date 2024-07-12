package com.reto.interbank.ms_transaction.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {
    private UUID transactionExternalId;
    private TransactionTypeDTO transactionType;
    private TransactionStatusDTO transactionStatus;
    private Double value;
    private LocalDateTime createdAt;

}
