package pe.interbank.secure.transaction.repository;

import pe.interbank.secure.transaction.model.Account;
import pe.interbank.secure.transaction.model.Transaction;

import java.util.UUID;

public interface TransactionRepository extends GenericRepository<Transaction, UUID> {
}
