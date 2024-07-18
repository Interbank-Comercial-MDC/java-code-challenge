package com.interbank.challenge.config.kafka;

import com.interbank.challenge.entities.Transaction;
import com.interbank.challenge.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {

	@Autowired
	private TransactionService transactionService;

	@KafkaListener(topics = "transactions-topic", groupId = "transaction-group")
	public void listenTransactionStatus(Transaction transaction) {
		log.info("Received transaction: {}", transaction);
		transactionService.updateTransactionStatus(transaction);
	}

}
