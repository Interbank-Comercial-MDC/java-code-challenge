package com.reto.interbank.ms_transaction.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.interbank.ms_transaction.dto.TransactionDTO;
import com.reto.interbank.ms_transaction.model.Transactions;
import com.reto.interbank.ms_transaction.service.TransactionService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
	@Autowired
	private TransactionService service;


	@PostMapping
	public Mono<ResponseEntity<Transactions>> createTransaction(@RequestBody Transactions transaction) {
	    return service.createTransaction(transaction)
	            .doOnError(error -> log.error("Error al crear la transacción: {}", error.getMessage()))
	            .doOnSuccess(createdTransaction -> log.info("Transacción creada con éxito: {}", createdTransaction.getId()))
	            .map(ResponseEntity::ok)
	            .defaultIfEmpty(ResponseEntity.notFound().build());
	}

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransactionDTO>> getTransactionById(@PathVariable UUID id) {
        return service.getTransaction(id)
                .doOnError(error -> log.error("Error al buscar la transacción con ID {}: {}", id, error.getMessage()))
                .doOnSuccess(transactionDTO -> log.info("Transacción encontrada con ID {}: {}", id, transactionDTO))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}