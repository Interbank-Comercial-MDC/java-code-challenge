package com.hexagonal.demoAntiFraud.application.useCases;

import com.hexagonal.demoAntiFraud.domain.ports.in.KafkaUseCase;
import com.hexagonal.demoAntiFraud.domain.ports.out.KafkaPort;
import org.springframework.stereotype.Service;

@Service
public class KafkaImpl implements KafkaUseCase {

    private final KafkaPort kafkaPort;

    public KafkaImpl(KafkaPort loadKafkaPort) {
        this.kafkaPort = loadKafkaPort;
    }

    @Override
    public void sendMessage(String topic, String message) {
        kafkaPort.send(topic, message);
    }
}