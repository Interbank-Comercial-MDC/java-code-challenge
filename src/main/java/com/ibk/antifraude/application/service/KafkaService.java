package com.ibk.antifraude.application.service;

import com.ibk.antifraude.application.ports.in.KafkaUseCase;
import com.ibk.antifraude.application.ports.out.LoadKafkaPort;
import org.springframework.stereotype.Service;


@Service
public class KafkaService implements KafkaUseCase {


    private final LoadKafkaPort loadKafkaPort;

    public KafkaService(LoadKafkaPort loadKafkaPort) {
        this.loadKafkaPort = loadKafkaPort;
    }

    @Override
    public void sendMessage(String topic, String message) {
        loadKafkaPort.send(topic, message);
    }
}
