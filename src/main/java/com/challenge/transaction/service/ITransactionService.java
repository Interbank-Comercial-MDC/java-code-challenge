package com.challenge.transaction.service;

import com.challenge.transaction.model.Transaction;

public interface ITransactionService {
  Transaction getTransaction(Integer transactionId);

  Transaction createTransaction(Transaction transaction);

  void updateTransactionState(Transaction transaction);
}
