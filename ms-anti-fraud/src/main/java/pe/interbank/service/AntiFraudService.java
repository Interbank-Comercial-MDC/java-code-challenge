package pe.interbank.service;

import pe.interbank.model.Transaction;
import reactor.core.publisher.Mono;

public interface AntiFraudService {
  Mono<Void> validateTransaction(Transaction transaction);
}
