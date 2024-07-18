package com.interbank.challenge.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponse {
	
	private String transactionExternalId;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private double value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
}	
