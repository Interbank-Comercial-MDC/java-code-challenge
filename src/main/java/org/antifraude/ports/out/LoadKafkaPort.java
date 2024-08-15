package org.antifraude.ports.out;

public interface LoadKafkaPort {
    void send(String topic, String message);
}
