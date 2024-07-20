package com.nttdata.topic.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nttdata.service.TransactionService;
import com.nttdata.topic.message.TransactionCreatedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TransactionService transactionService;

    public TransactionConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "${kafka.topic.transactions.trx-topic-consumer}")
    public void consumeTransactionCreatedMessage(String message) {
        TransactionCreatedMessage transaction = jsonToTransactionCreatedMessage(message);
        logger.info("Received transaction created message: {}", transaction);
        transactionService.validate(transaction);
    }

    public static TransactionCreatedMessage jsonToTransactionCreatedMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.registerModule(new Jdk8Module());
            mapper.registerModule(new JavaTimeModule());

            return mapper.readValue(message, TransactionCreatedMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
