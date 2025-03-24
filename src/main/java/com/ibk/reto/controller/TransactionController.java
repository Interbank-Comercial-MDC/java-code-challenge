package com.ibk.reto.controller;

import com.ibk.reto.model.Transaction;
import com.ibk.reto.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Reto Tecnico IBK.
 *
 * @author Alvaro Cubas Huarca
 * @version 1.0
 * @since 2025-03-23
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;
    public TransactionController(TransactionService service) {
        this.service = service;
    }
    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return service.createTransaction(transaction);
    }
    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable UUID id) {
        return service.getTransactionById(id);
    }
}
