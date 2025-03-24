package pe.interbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.interbank.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
