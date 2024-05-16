package com.java.code.challenge.transaction.validation.api.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class TransactionRequest {

	private UUID accountExternalIdDebit;
	private UUID accountExternalIdCredit;
	private String tranType;
	private double value;
	private String status;
}
