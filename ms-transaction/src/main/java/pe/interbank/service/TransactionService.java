package pe.interbank.service;

import pe.interbank.model.DTO.TransactionRequest;
import pe.interbank.model.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionService {

  Mono<Transaction> getTransaction(String accountExternalId);

  Mono<Transaction> sendTransaction(TransactionRequest transactionRq);

  Mono<Void> updateTransactionStatus(Transaction transaction);
}
