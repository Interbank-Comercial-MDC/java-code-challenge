package pe.interbank.secure.transaction.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TransactionResponseDto(UUID transactionExternalId, String transactionStatus) {
}
