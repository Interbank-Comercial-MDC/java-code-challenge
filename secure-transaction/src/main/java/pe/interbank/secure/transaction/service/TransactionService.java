package pe.interbank.secure.transaction.service;

import pe.interbank.secure.transaction.dto.TransactionDetailsResponseDto;
import pe.interbank.secure.transaction.dto.TransactionRequestDto;
import pe.interbank.secure.transaction.dto.TransactionResponseDto;
import pe.interbank.secure.transaction.model.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionService {

  TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto) throws Exception;
  TransactionDetailsResponseDto getTransaction(UUID transactionExternalId) throws Exception;
  void antiFraudValidate(Transaction transaction);
  void sendToAntiFraudService(Transaction transaction);
}
