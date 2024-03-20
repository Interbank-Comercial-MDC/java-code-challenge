package pe.interbank.secure.transaction.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequestDto(UUID accountExternalIdDebit,
                                    UUID accountExternalIdCredit,
                                    Integer transferTypeId,
                                    Integer transactionTypeId,
                                    BigDecimal value) {
}
