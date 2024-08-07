package com.interbank.challenge.entity.dto.response;

import lombok.Data;

@Data
public class TransactionResponse {
    private String transactionId;
    private TransactionType type;
    private TypeStatus status;
    private Integer value;
    private String date;

}
