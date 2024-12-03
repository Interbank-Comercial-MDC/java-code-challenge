package com.prueba.crea.msgestionatransaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatusUpdate {
    @JsonProperty("transactionExternalId")
    private String transactionExternalId;
    @JsonProperty("transactionType")
    private TransactionType transactionType;
    @JsonProperty("transactionStatus")
    private TransactionStatus transactionStatus;
    @JsonProperty("value")
    private Double value;
    @JsonProperty("createdAt")
    private String createdAt;
}
