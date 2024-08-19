package com.lgutierrez.antifraudService.kafka;

import com.lgutierrez.transactionService.entity.Transaction;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventProducer.class);

    private NewTopic topic;

    private KafkaTemplate<String, Transaction> kafkaTemplate;

    public EventProducer(NewTopic topic, KafkaTemplate<String,Transaction> kafkaTemplate){
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Transaction transaction){
        LOGGER.info(String.format("Transaction event => %s", transaction.toString()));

        //create Message
        Message<Transaction> transactionMessage = MessageBuilder.withPayload(transaction)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(transactionMessage);
    }

}
