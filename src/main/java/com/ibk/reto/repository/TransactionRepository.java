package com.ibk.reto.repository;

import com.ibk.reto.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Reto Tecnico IBK.
 *
 * @author Alvaro Cubas Huarca
 * @version 1.0
 * @since 2025-03-23
 */
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
