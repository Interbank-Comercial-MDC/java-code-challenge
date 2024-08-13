package com.hexagonal.demoAntiFraud.domain.ports.out;

public interface KafkaPort {
    void send(String topic, String message);
}