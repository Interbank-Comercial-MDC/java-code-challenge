package pe.interbank.secure.transaction.repository;

import pe.interbank.secure.transaction.model.Account;

import java.util.UUID;

public interface AccountRepository extends GenericRepository<Account, UUID> {
}
