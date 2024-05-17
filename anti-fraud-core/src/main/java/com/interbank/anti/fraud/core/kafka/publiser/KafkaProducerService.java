package com.interbank.anti.fraud.core.kafka.publiser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "fraud-topic";

    public void sendMessage(String message) {
        try {
            log.info("[ANTI-FRAUD] send Kafka init...");
            kafkaTemplate.send(TOPIC, message);
            log.info("[ANTI-FRAUD] send Kafka end");
        } catch (Exception e) {
            log.error("[ANTI-FRAUD-SEND] message:{}, trace:{}", e.getMessage(), e);
        }
    }
}
