package pe.interbank.service.impl;

import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pe.interbank.model.Transaction;
import pe.interbank.service.AntiFraudService;
import pe.interbank.util.JsonUtils;
import pe.interbank.util.StatusTransaction;
import reactor.core.publisher.Mono;

import static pe.interbank.util.Contants.MAX_AMOUNT;
import static pe.interbank.util.Contants.TOPIC_TRANSACTION_VALIDATED;

@Service
public class AntiFraudServiceImpl implements AntiFraudService {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AntiFraudServiceImpl.class);

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final JsonUtils jsonUtils;

  public AntiFraudServiceImpl(KafkaTemplate<String, String> kafkaTemplate, JsonUtils jsonUtils) {
    this.kafkaTemplate = kafkaTemplate;
    this.jsonUtils = jsonUtils;
  }

  @Override
  public Mono<Void> validateTransaction(Transaction transaction) {
    logger.info("Validating transaction: {}", transaction);

    updateTransactionStatus(transaction);

    String transactionValidated = jsonUtils.toJson(transaction);

    return Mono.fromFuture(kafkaTemplate.send(TOPIC_TRANSACTION_VALIDATED, transactionValidated))
        .doOnSuccess(s -> logger.info("Transaction VALIDATED sent to kafka: {}", transactionValidated))
        .then();
  }

  private void updateTransactionStatus(Transaction transaction) {
    if (transaction.getValue() > MAX_AMOUNT) {
      transaction.setStatus(StatusTransaction.RECHAZADO.getDescription());
    } else {
      transaction.setStatus(StatusTransaction.APROBADO.getDescription());
    }
  }
}
