package com.interbank.challenge.service;

import com.interbank.challenge.dto.request.TransactionRequest;
import com.interbank.challenge.dto.response.TransactionResponse;
import com.interbank.challenge.entities.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

	Transaction createTransaction(TransactionRequest request);
	List<Transaction> getAllTransactions();
	void updateTransactionStatus(Transaction transaction);
	List<TransactionResponse> getTransactionsByAccountExternalId(UUID accountExternalId);
}
