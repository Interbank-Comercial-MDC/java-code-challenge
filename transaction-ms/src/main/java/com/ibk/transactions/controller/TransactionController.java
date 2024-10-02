package com.ibk.transactions.controller;

import com.ibk.transactions.dto.CreateTransactionRequestDTO;
import com.ibk.transactions.dto.TransactionResponseDTO;
import com.ibk.transactions.entity.Transaction;
import com.ibk.transactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody CreateTransactionRequestDTO request) {
        Transaction transaction = transactionService.createTransaction(request);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransaction(@PathVariable Long id) {
        TransactionResponseDTO response = transactionService.getTransaction(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

