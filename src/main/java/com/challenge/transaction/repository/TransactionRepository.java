package com.challenge.transaction.repository;

import com.challenge.transaction.repository.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

  @Transactional
  @Modifying
  @Query(
      "update TransactionEntity t set t.transactionStatusId = :transactionStatusId "
          + "where t.transactionExternalId = :transactionExternalId")
  Integer updateTransactionStatusId(
      @Param(value = "transactionExternalId") Integer transactionExternalId,
      @Param(value = "transactionStatusId") Integer transactionStatusId);
}
