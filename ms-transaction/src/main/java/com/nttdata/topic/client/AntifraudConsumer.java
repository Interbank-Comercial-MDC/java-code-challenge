package com.nttdata.topic.client;

import com.nttdata.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AntifraudConsumer {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TransactionService transactionService;

    public AntifraudConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "${kafka.topic.transactions.trx-rejected-consumer}")
    public void transactionRejectedTopic(String transactionId) {
        transactionService.updateRejectedTransaction(transactionId);
    }

    @KafkaListener(topics = "${kafka.topic.transactions.trx-approved-consumer}")
    public void transactionApprovedTopic(String transactionId) {
        transactionService.updateApprovedTransaction(transactionId);
    }

}
