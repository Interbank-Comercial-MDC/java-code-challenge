package com.reto.interbank.ms_transaction.service;

import java.util.UUID;

import com.reto.interbank.ms_transaction.dto.TransactionDTO;
import com.reto.interbank.ms_transaction.model.Transactions;

import reactor.core.publisher.Mono;

public interface TransactionService {

	public Mono<Transactions> createTransaction(Transactions transaction);
	
	public Mono<TransactionDTO> getTransaction(UUID id);
	
}
