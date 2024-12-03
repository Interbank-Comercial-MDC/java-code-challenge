package com.prueba.antifraude.msantifraude.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class TransactionValidate {
    private String transactionExternalId;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private Double value;
    private String createdAt;
}
