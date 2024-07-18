package com.interbank.challenge.repository;

import com.interbank.challenge.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.accountExternalIdDebit = :accountExternalId OR t.accountExternalIdCredit = :accountExternalId")
    List<Transaction> findByAccountExternalId(@Param("accountExternalId") UUID accountExternalId);

}
