package com.nttdata.topic.client;

import com.nttdata.entity.Transaction;
import com.nttdata.helper.Util;
import com.nttdata.topic.message.TransactionCreatedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AntifraudProducer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${kafka.topic.transactions.trx-topic-producer}")
    private String trxTopicProducer;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public AntifraudProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransactionCreatedMessage(Transaction transaction) {
        try {
            TransactionCreatedMessage messageDto = TransactionCreatedMessage.builder()
                    .transactionExternalId(transaction.getId())
                    .value(transaction.getValue()).build();

            String message = Util.toJsonString(messageDto);

            kafkaTemplate.send(trxTopicProducer, message);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

    }
}
