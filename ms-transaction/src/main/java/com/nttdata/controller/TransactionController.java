package com.nttdata.controller;

import com.nttdata.controller.request.TransactionReq;
import com.nttdata.controller.response.CreateTransactionRes;
import com.nttdata.controller.response.TransactionRes;
import com.nttdata.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<CreateTransactionRes> createTransaction(@RequestBody TransactionReq request) {
        CreateTransactionRes response = transactionService.createTransaction(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionRes> getTransaction(@PathVariable String transactionId) {
        TransactionRes response = transactionService.getTransaction(transactionId);
        return ResponseEntity.ok(response);
    }

}
