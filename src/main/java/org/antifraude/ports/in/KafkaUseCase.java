package org.antifraude.ports.in;

public interface KafkaUseCase {
    void sendMessage(String topic, String message);
}
