package com.lgutierrez.transactionService.controller;

import com.lgutierrez.transactionService.dto.TransactionDTO;
import com.lgutierrez.transactionService.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable("id") UUID transactionId) throws Exception {
        TransactionDTO transactionDTO = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transactionDTO);
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        TransactionDTO transactionDTO1 = transactionService.registerTransaction(transactionDTO);
        return new ResponseEntity<>(transactionDTO1, HttpStatus.CREATED);
    }
}
