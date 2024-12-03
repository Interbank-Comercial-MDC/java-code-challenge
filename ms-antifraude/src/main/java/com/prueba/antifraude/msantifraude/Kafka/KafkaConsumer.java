package com.prueba.antifraude.msantifraude.Kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.antifraude.msantifraude.model.TransactionNew;
import com.prueba.antifraude.msantifraude.service.AntiFraudeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

@Configuration
public class KafkaConsumer {

    @Autowired
    AntiFraudeService antiFraudeService;

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "topic-crea-transaction", groupId = "my-group-id")
    public void consumerTransaction(String message) throws IOException {
        logger.info("Transaction CREADA: "  + message);
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            TransactionNew transaction = objectMapper.readValue(message, TransactionNew.class);
            antiFraudeService.validaAntifraude(transaction);

        } catch (Exception e) {
            logger.error("Error al convertir JSON a Entity: " + e.getMessage());
        }


    }
}
