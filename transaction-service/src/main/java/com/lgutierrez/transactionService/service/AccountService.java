package com.lgutierrez.transactionService.service;

import com.lgutierrez.transactionService.dto.AccountDTO;
import com.lgutierrez.transactionService.dto.TransactionDTO;

public interface AccountService {
    AccountDTO registerAccount(AccountDTO accountDTO) throws Exception;
}
