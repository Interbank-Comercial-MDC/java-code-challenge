package com.interbank.pe.ms_transaction.data.repositories;

import com.interbank.pe.ms_transaction.data.entities.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Integer> {

}
