package com.prueba.crea.msgestionatransaction.controller;

import com.prueba.crea.msgestionatransaction.model.Transaction;
import com.prueba.crea.msgestionatransaction.model.TransactionRequest;
import com.prueba.crea.msgestionatransaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService txService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @RequestBody TransactionRequest request) {

        Transaction txCreada = txService.sendTransaction(request).block();

        return ResponseEntity.ok(txCreada);
    }

}
