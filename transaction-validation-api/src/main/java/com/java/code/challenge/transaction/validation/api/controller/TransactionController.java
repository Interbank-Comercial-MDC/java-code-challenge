package com.java.code.challenge.transaction.validation.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.code.challenge.transaction.validation.api.dto.TransactionRequest;
import com.java.code.challenge.transaction.validation.api.dto.TransactionResponse;
import com.java.code.challenge.transaction.validation.api.model.Transaction;
import com.java.code.challenge.transaction.validation.api.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	@Autowired
	private TransactionService service;

	@PostMapping("/create")
	public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest request) {
		Transaction createTx = service.createTransaction(request);
		return ResponseEntity.ok(createTx);

	}

	@GetMapping
	public ResponseEntity<List<Transaction>> getAllTrasanctions() {
		List<Transaction> transactions = service.getAllTransactions();
		return ResponseEntity.ok(transactions);
	}
	
	@GetMapping("/search")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountExternalId(@RequestParam UUID accountExternalId) {
        List<TransactionResponse> response = service.getTransactionsByAccountExternalId(accountExternalId);
        return ResponseEntity.ok(response);
    }

}
