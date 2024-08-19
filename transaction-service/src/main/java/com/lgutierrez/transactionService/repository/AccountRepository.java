package com.lgutierrez.transactionService.repository;

import com.lgutierrez.transactionService.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

}
