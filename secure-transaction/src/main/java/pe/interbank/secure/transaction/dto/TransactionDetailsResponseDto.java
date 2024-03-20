package pe.interbank.secure.transaction.dto;

import lombok.Builder;
import pe.interbank.secure.transaction.model.TransactionStatus;
import pe.interbank.secure.transaction.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TransactionDetailsResponseDto(UUID transactionExternalId,
                                            TransactionTypeDto transactionType,
                                            TransactionStatusDto transactionStatus,
                                            BigDecimal value,
                                            LocalDateTime createdAt) {
}
