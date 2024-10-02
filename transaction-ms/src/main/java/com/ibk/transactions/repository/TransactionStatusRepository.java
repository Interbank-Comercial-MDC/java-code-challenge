package com.ibk.transactions.repository;

import com.ibk.transactions.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, Long> {
    TransactionStatus findByName(String name);
}
