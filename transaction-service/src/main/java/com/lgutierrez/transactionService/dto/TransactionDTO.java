package com.lgutierrez.transactionService.dto;

import com.lgutierrez.transactionService.exception.BadRequestException;
import java.util.UUID;

public record TransactionDTO(
        UUID accountExternalIdDebit,
        UUID accountExternalIdCredit,
        String transferTypeId,
        Double value
) {
    public TransactionDTO {
        if (accountExternalIdDebit == null && accountExternalIdCredit == null) {
            throw new BadRequestException("account data not should be null or empty");
        }

        if (transferTypeId == null) {
            throw new BadRequestException("invalid transfer type ID");
        }

        if (value == null) {
            throw new BadRequestException("invalid value of amount");
        }
    }
}
