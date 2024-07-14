package com.challenge.transaction.service;

import com.challenge.transaction.consumer.dto.TransactionDTO;

public interface IAntiFraudService {

  void evaluateTransaction(TransactionDTO transactionDTO);
}
