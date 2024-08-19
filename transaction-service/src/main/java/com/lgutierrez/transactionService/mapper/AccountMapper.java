package com.lgutierrez.transactionService.mapper;

import com.lgutierrez.transactionService.dto.AccountDTO;
import com.lgutierrez.transactionService.entity.Account;

public class AccountMapper {
    public static AccountDTO mapToAccountDTO(Account account) {
        return new AccountDTO(account.getStatus(), account.getType());
    }
}
