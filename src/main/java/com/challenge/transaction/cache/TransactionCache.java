package com.challenge.transaction.cache;

import com.challenge.transaction.model.Transaction;
import com.challenge.transaction.repository.TransactionRepository;
import com.challenge.transaction.repository.converter.TransactionMapper;

public interface TransactionCache {

  Transaction getTransaction(Integer transactionId);

  void deleteTransactionCache(Integer transactionExternalId);
}
