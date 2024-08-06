package com.interbank.challenge.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AntifraudeConsumerServiceImpl implements AntifraudeConsumerService {

    @Override
    @KafkaListener(topics = "registro-events", groupId = "Interbank")
    public void listen(String message) {
        System.out.println("Mensaje recibido: " + message);
    }
}
