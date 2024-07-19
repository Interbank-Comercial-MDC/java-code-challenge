package com.ibk.antifraude.application.ports.in;

public interface KafkaUseCase {
    void sendMessage(String topic, String message);
}
