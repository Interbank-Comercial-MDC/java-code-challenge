package com.java.code.challenge.transaction.validation.api.service;

import java.util.List;
import java.util.UUID;

import com.java.code.challenge.transaction.validation.api.dto.TransactionRequest;
import com.java.code.challenge.transaction.validation.api.dto.TransactionResponse;
import com.java.code.challenge.transaction.validation.api.model.Transaction;

public interface TransactionService {

	Transaction createTransaction(TransactionRequest request);

	List<Transaction> getAllTransactions();

	void updateTransactionStatus(Transaction transaction);

	List<TransactionResponse> getTransactionsByAccountExternalId(UUID accountExternalId);
}
