package com.hexagonal.demoAntiFraud.infrastructure.repository;

import com.hexagonal.demoAntiFraud.domain.models.RequestTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<RequestTransaction, Long> {

}