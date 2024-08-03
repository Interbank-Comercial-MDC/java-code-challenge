package com.interbank.pe.ms_transaction.data.repositories;

import com.interbank.pe.ms_transaction.data.entities.TransactionType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends ReactiveCrudRepository<TransactionType, Integer> {

}
