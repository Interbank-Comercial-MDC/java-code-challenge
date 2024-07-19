package com.ibk.antifraude.application.ports.out;

public interface LoadKafkaPort {
    void send(String topic, String message);
}
