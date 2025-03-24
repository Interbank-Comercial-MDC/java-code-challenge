package pe.interbank.kafka;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import pe.interbank.model.Transaction;
import pe.interbank.service.TransactionService;
import pe.interbank.util.JsonUtils;

import static pe.interbank.util.Contants.*;

@Configuration
public class TransactionConsumer {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TransactionConsumer.class);

  @Autowired
  private TransactionService transactionService;

  private final JsonUtils jsonUtils;

  public TransactionConsumer(JsonUtils jsonUtils) {
    this.jsonUtils = jsonUtils;
  }

  @KafkaListener(topics = TOPIC_TRANSACTION_VALIDATED, groupId = GROUP_ID_INTERBANK)
  public void consumeTransaction(String message) {
    logger.info("Topic {}: Consumed message: {}", TOPIC_TRANSACTION_VALIDATED, message);
    transactionService.updateTransactionStatus(jsonUtils.fromJson(message, Transaction.class)).subscribe();
  }
}
