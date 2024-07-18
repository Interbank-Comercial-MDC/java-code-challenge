package com.interbank.challenge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
public class TransactionRequest {

	private UUID accountExternalIdDebit;
	private UUID accountExternalIdCredit;

	@Schema(
			description = "transaction type",
			example = "1"
	)
	private String tranType;

	@Schema(
			description = "transaction value",
			example = "1000"
	)
	@Positive(message = "El valor debe ser un numero positivo")
	private double value;
}
