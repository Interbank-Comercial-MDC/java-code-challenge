package com.interbank.anti.fraud.core.repository;

import com.interbank.anti.fraud.core.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AntiFraudRepository extends JpaRepository<Transaction, Long> {
}
