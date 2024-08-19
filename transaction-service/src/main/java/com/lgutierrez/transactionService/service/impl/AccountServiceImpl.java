package com.lgutierrez.transactionService.service.impl;

import com.lgutierrez.transactionService.dto.AccountDTO;
import com.lgutierrez.transactionService.entity.Account;
import com.lgutierrez.transactionService.mapper.AccountMapper;
import com.lgutierrez.transactionService.repository.AccountRepository;
import com.lgutierrez.transactionService.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO registerAccount(AccountDTO accountDTO) throws Exception {
        LocalDateTime now = LocalDateTime.now();

        Account account = new Account(null,
                                      accountDTO.accountStatus(),
                                      accountDTO.accountType(),
                                      now,
                                      now,
                                      null);
        Account newAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDTO(newAccount);
    }
}
