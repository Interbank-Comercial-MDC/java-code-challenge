package org.antifraude.application;

import org.antifraude.ports.in.KafkaUseCase;
import org.antifraude.ports.out.LoadKafkaPort;
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
