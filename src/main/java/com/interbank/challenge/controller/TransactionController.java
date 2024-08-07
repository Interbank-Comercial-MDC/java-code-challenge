package com.interbank.challenge.controller;

import com.interbank.challenge.entity.TransactionDocument;
import com.interbank.challenge.entity.dto.request.TransactionRequest;
import com.interbank.challenge.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/save")
    @Operation(summary = "Metodo createTransaction", description = "Crea una operación")
    public Mono<TransactionRequest> createTransaction(@RequestHeader("Authorization") String token, @Valid  @RequestBody TransactionRequest transaction) {
        return transactionService.createTransaction(transaction);

    }

    @GetMapping("/list/{id}")
    @Operation(summary = "Metodo getTransaction", description = "Devuelve una operacion")
    public Mono<TransactionDocument> getTransaction(@RequestHeader("Authorization") String token, @Valid @PathVariable String id) {
        return transactionService.getTransactionById(id);
    }
}
