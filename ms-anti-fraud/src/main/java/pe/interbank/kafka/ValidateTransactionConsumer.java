package pe.interbank.kafka;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import pe.interbank.model.Transaction;
import pe.interbank.service.AntiFraudService;
import pe.interbank.util.JsonUtils;

import static pe.interbank.util.Contants.GROUP_ID_INTERBANK;
import static pe.interbank.util.Contants.TOPIC_TRANSACTION_CREATED;

@Configuration
public class ValidateTransactionConsumer {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ValidateTransactionConsumer.class);

  @Autowired
  private AntiFraudService antiFraudService;

  private final JsonUtils jsonUtils;

  public ValidateTransactionConsumer(JsonUtils jsonUtils) {
    this.jsonUtils = jsonUtils;
  }

  @KafkaListener(topics = TOPIC_TRANSACTION_CREATED, groupId = GROUP_ID_INTERBANK)
  public void consumeTransaction(String message) {
    logger.info("Consumed message: {}", message);
    antiFraudService.validateTransaction(jsonUtils.fromJson(message, Transaction.class)).subscribe();
  }
}
