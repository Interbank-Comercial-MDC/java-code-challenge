package com.nttdata.repository;

import com.nttdata.entity.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Modifying
    @Transactional
    @Query("UPDATE Transaction e SET e.status = :status WHERE e.id = :transactionId")
    int updateStatusById(String transactionId, String status);

}