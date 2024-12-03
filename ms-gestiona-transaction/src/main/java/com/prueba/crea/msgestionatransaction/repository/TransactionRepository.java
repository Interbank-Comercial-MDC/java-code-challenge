package com.prueba.crea.msgestionatransaction.repository;

import com.prueba.crea.msgestionatransaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
