package com.hexagonal.demoAntiFraud.domain.ports.in;

public interface KafkaUseCase {
    void sendMessage(String topic, String message);
}
