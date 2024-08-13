package com.hexagonal.demoAntiFraud.infrastructure.adapters;

import com.hexagonal.demoAntiFraud.domain.ports.out.KafkaPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaAdapter implements KafkaPort {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String topic, String message) {
        log.info("Sending message to topic {}:{}", topic, message);
        kafkaTemplate.send(topic, message);
    }
}