package com.ibk.antifraude.infrastructure.adapters.out.persistence;

import com.ibk.antifraude.domain.model.FinancialTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialTransactionRepository extends ReactiveCrudRepository<FinancialTransaction, Long> {

}
