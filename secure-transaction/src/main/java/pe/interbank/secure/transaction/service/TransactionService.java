package pe.interbank.secure.transaction.service;

import pe.interbank.secure.transaction.dto.TransactionRequestDto;
import pe.interbank.secure.transaction.model.Transaction;

public interface TransactionService {

  Transaction createTransaction(TransactionRequestDto transactionRequestDto) throws Exception;
  Transaction getTransaction(String transactionExternalId);
  void sendToAntiFraudService(Transaction transaction);
}
