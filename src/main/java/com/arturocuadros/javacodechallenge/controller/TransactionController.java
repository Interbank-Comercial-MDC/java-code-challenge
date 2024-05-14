package com.arturocuadros.javacodechallenge.controller;

import com.arturocuadros.javacodechallenge.dto.TransactionRequest;
import com.arturocuadros.javacodechallenge.dto.TransactionSaveResponse;
import com.arturocuadros.javacodechallenge.dto.TransactionUpdateResponse;
import com.arturocuadros.javacodechallenge.exception.TransactionNotFoundException;
import com.arturocuadros.javacodechallenge.model.Transaction;
import com.arturocuadros.javacodechallenge.serviceImpl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionServiceImpl transactionService;

    @GetMapping("/transaction")
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionSaveResponse> createTransaction(
            @RequestBody TransactionRequest request
    ) {
        TransactionSaveResponse response = transactionService.save(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/transaction/{transactionId}")
    public ResponseEntity<TransactionUpdateResponse> updateTransaction(
            @PathVariable("transactionId") String transactionId,
            @RequestBody TransactionRequest request
    ) {
        TransactionUpdateResponse response = transactionService.update(transactionId, request);
        return ResponseEntity.ok(response);
    }
}
