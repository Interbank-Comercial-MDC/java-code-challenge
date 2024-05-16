package com.java.code.challenge.transaction.validation.api.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.java.code.challenge.transaction.validation.api.model.Transaction;
import com.java.code.challenge.transaction.validation.api.service.TransactionService;

@Service
public class KafkaService {

	@Autowired
	private TransactionService transactionService;

	@KafkaListener(topics = "transactions-topic", groupId = "transaction-group")
	public void listenTransactionStatus(Transaction transaction) {
		System.out.println("Received: " + transaction);
		transactionService.updateTransactionStatus(transaction);
	}
}
