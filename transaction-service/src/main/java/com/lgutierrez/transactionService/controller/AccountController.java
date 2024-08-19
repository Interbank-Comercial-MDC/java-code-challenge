package com.lgutierrez.transactionService.controller;

import com.lgutierrez.transactionService.dto.AccountDTO;
import com.lgutierrez.transactionService.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) throws Exception {
        AccountDTO accountDTO1 = accountService.registerAccount(accountDTO);
        return new ResponseEntity<>(accountDTO1, HttpStatus.CREATED);
    }
}
