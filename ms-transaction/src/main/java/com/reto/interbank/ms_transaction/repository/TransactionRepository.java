package com.reto.interbank.ms_transaction.repository;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.reto.interbank.ms_transaction.model.Transactions;

public interface TransactionRepository extends R2dbcRepository<Transactions, UUID> {
}