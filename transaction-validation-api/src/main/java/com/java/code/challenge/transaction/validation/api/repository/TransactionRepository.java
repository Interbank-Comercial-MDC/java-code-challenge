package com.java.code.challenge.transaction.validation.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.code.challenge.transaction.validation.api.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	@Query("SELECT t FROM Transaction t WHERE t.accountExternalIdDebit = :accountExternalId OR t.accountExternalIdCredit = :accountExternalId")
    List<Transaction> findByAccountExternalId(@Param("accountExternalId") UUID accountExternalId);
}
