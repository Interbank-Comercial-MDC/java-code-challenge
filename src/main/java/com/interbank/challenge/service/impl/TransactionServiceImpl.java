package com.interbank.challenge.service.impl;

import com.interbank.challenge.dto.request.TransactionRequest;
import com.interbank.challenge.dto.response.TransactionResponse;
import com.interbank.challenge.entities.Transaction;
import com.interbank.challenge.exceptions.BusinessException;
import com.interbank.challenge.mapper.TransactionMapper;
import com.interbank.challenge.repository.TransactionRepository;
import com.interbank.challenge.service.TransactionService;
import com.interbank.challenge.util.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final KafkaTemplate<String, Transaction> kafkaTemplate;
	private final TransactionMapper transactionMapper;


	@Override
	public Transaction createTransaction(TransactionRequest request) {
		Transaction transaction = transactionMapper.toEntity(request);
		Transaction savedTransaction = transactionRepository.save(transaction);
		kafkaTemplate.send(Constantes.TOPIC, savedTransaction.getId().toString(), savedTransaction);
		return savedTransaction;
	}


	@Override
	public void updateTransactionStatus(Transaction transaction) {
		Transaction existingTransaction = transactionRepository.findById(transaction.getId())
				.orElseThrow(() -> new BusinessException(Constantes.BAD_REQUEST, HttpStatus.NOT_FOUND, Constantes.ID_NOT_FOUND + transaction.getId()));

		existingTransaction.setStatus(transaction.getValue() > 1000 ? Constantes.ESTADO_RECHAZADO : Constantes.ESTADO_APROBADO);
		transactionRepository.save(existingTransaction);
	}

	@Override
	public List<TransactionResponse> getTransactionsByAccountExternalId(UUID accountExternalId) {
		List<Transaction> transactions = transactionRepository.findByAccountExternalId(accountExternalId);

		if (transactions.isEmpty()) {
			throw new BusinessException(Constantes.BAD_REQUEST, HttpStatus.NOT_FOUND, Constantes.NOT_FOUND + accountExternalId);
		}
		return transactions.stream()
				.map(transactionMapper::toResponse)
				.toList();
	}


	@Override
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

}
