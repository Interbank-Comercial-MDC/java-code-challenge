package com.interbank.challenge.controller;

import com.interbank.challenge.entity.dto.request.TransactionRequest;
import com.interbank.challenge.entity.dto.response.TransactionResponse;
import com.interbank.challenge.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/save")
    @Operation(summary = "Metodo createTransaction", description = "Crea una operación")
    public Mono<ResponseEntity<Map<String, String>>>  createTransaction(@RequestHeader("Authorization") String token, @Valid  @RequestBody TransactionRequest transaction) {
        return transactionService.createTransaction(transaction)
                .map(transactionId -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("idTransaction", transactionId.getId());
                    response.put("message", "El registro se hizo correctamente");
                    return ResponseEntity.ok(response);
                });

    }

    @GetMapping("/list/{id}")
    @Operation(summary = "Metodo getTransaction", description = "Devuelve una operacion")
    public Mono<TransactionResponse> getTransaction(@RequestHeader("Authorization") String token, @Valid @PathVariable String id) {
        return transactionService.getTransactionById(id);
    }
}
