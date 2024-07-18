package com.interbank.challenge.controller;

import com.interbank.challenge.dto.request.TransactionRequest;
import com.interbank.challenge.dto.response.TransactionResponse;
import com.interbank.challenge.entities.Transaction;
import com.interbank.challenge.service.TransactionService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "TRANSACTION", description = "Operaciones permitidas sobre la entidad Transaction")
public class TransactionController {

    private final TransactionService service;

    @Operation(summary = "Crear una nueva Transaccion")
    @ApiResponse(responseCode = "201", description = "TRANSACTION creado exitosamente")
    @PostMapping(value ="/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Valid TransactionRequest request) {
        Transaction serviceTransaction = service.createTransaction(request);
        return new ResponseEntity<>(serviceTransaction, HttpStatus.CREATED);

    }

    @Operation(summary = "Listar todas las Transacciones")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Transaction>> getAllTrasanctions() {
        List<Transaction> transactions = service.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @Operation(summary = "Traer una Transaccion por su UUID")
    @GetMapping(value ="/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountExternalId(@RequestParam UUID accountExternalId) {
        List<TransactionResponse> response = service.getTransactionsByAccountExternalId(accountExternalId);
        return ResponseEntity.ok(response);
    }

}
