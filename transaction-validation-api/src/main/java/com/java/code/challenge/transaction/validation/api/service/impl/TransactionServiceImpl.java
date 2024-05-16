package com.java.code.challenge.transaction.validation.api.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.java.code.challenge.transaction.validation.api.dto.TransactionRequest;
import com.java.code.challenge.transaction.validation.api.dto.TransactionResponse;
import com.java.code.challenge.transaction.validation.api.dto.TransactionStatus;
import com.java.code.challenge.transaction.validation.api.dto.TransactionType;
import com.java.code.challenge.transaction.validation.api.exception.TransactionNotFoundException;
import com.java.code.challenge.transaction.validation.api.model.Transaction;
import com.java.code.challenge.transaction.validation.api.repository.TransactionRepository;
import com.java.code.challenge.transaction.validation.api.service.TransactionService;
import com.java.code.challenge.transaction.validation.api.util.Constantes;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private KafkaTemplate<String, Transaction> kafkaTemplate;

	

	@Override
	public Transaction createTransaction(TransactionRequest request) {
		return Optional.of(request).filter(tx -> tx.getValue() >= 0).map(tx -> {
			Transaction transaction = new Transaction();
			transaction.setAccountExternalIdDebit(tx.getAccountExternalIdDebit());
			transaction.setAccountExternalIdCredit(tx.getAccountExternalIdCredit());
			transaction.setTranType(tx.getTranType());
			transaction.setValue(tx.getValue());
			transaction.setStatus(Constantes.ESTADO_PENDIENTE);

			Transaction savedTransaction = transactionRepository.save(transaction);
			kafkaTemplate.send(Constantes.TOPIC, transaction.getId().toString(), transaction);
			return savedTransaction;
		}).orElseThrow(() -> new IllegalArgumentException(Constantes.EX_NEGATIVO));
	}

	@Override
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

	@Override
	public void updateTransactionStatus(Transaction transaction) {
		transactionRepository.findById(transaction.getId()).map(tx -> {
			tx.setStatus(tx.getValue() > 1000 ? Constantes.ESTADO_RECHAZADO : Constantes.ESTADO_APROBADO);
			return transactionRepository.save(tx);
		}).orElseThrow(() -> new NoSuchElementException(Constantes.EX_ID_NOT_FOUND + transaction.getId()));
	}

	@Override
	public List<TransactionResponse> getTransactionsByAccountExternalId(UUID accountExternalId) {
		List<Transaction> transactions = transactionRepository.findByAccountExternalId(accountExternalId);

		return Optional.of(transactions).filter(trans -> !trans.isEmpty())
				.map(trans -> trans.stream().map(transaction -> mapToResponse(transaction, accountExternalId))
						.collect(Collectors.toList()))
				.orElseThrow(() -> new TransactionNotFoundException(Constantes.NOT_FOUND + accountExternalId));
	}

	private TransactionResponse mapToResponse(Transaction transaction, UUID accountExternalId) {
		TransactionResponse response = new TransactionResponse();
		response.setTransactionExternalId(accountExternalId.toString());
		response.setTransactionType(new TransactionType(transaction.getTranType()));
		response.setTransactionStatus(new TransactionStatus(transaction.getStatus()));
		response.setValue(transaction.getValue());
		response.setCreatedAt(transaction.getCreatedAt());
		return response;
	}

}
