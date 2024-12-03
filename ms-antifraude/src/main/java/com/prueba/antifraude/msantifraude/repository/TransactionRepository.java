package com.prueba.antifraude.msantifraude.repository;

import com.prueba.antifraude.msantifraude.model.TransactionNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionNew, String> {
}
