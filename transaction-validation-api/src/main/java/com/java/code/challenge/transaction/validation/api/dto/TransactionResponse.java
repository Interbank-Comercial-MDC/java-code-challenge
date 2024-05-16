package com.java.code.challenge.transaction.validation.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransactionResponse {
	
	private String transactionExternalId;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private double value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
}	
