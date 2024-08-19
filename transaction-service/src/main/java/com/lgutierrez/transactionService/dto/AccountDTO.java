package com.lgutierrez.transactionService.dto;

import com.lgutierrez.transactionService.enums.AccountStatus;
import com.lgutierrez.transactionService.enums.AccountType;

public record AccountDTO(
        AccountStatus accountStatus,
        AccountType accountType
) {
    public AccountDTO {
        if (accountStatus == null){
            throw new IllegalArgumentException("accountStatus cannot be null");
        }
        if (accountType == null){
            throw new IllegalArgumentException("accountType cannot be null");
        }

    }
}
