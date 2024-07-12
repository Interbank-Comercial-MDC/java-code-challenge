package com.reto.interbank.ms_anti_fraud.listener;

import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Slf4j
@Component
public class KafkaFraudListener {

    private static final String TOPIC_SEND_FRAUD = "topicSendFraud";
    private static final String TOPIC_UPDATE_TRANSACTION_STATUS = "topicUpdateTransactionStatus";
    
    private static final String STATUS_APROBADO = "APROBADO";
    private static final String STATUS_RECHAZADO = "RECHAZADO";
    private static final Integer AMOUNT_LIMIT = 1000;
    private final ObjectMapper objectMapper;
    private final KafkaReceiver<String, String> kafkaReceiver;
    private final KafkaSender<String, String> kafkaSender;

    public KafkaFraudListener(KafkaReceiver<String, String> kafkaReceiver,
                              ObjectMapper objectMapper,
                              KafkaSender<String, String> kafkaSender) {
        this.kafkaReceiver = kafkaReceiver;
        this.objectMapper = objectMapper;
        this.kafkaSender = kafkaSender;
    }

    @PostConstruct
    public void startConsumeTopic() {
        consumeTopics();
    }

    private void consumeTopics() {
        kafkaReceiver.receive()
                .doOnNext(record -> {
                    String topic = record.topic();
                    String value = record.value();
                    log.info("Received message from topic {}: {}", topic, value);

                    if (TOPIC_SEND_FRAUD.equals(topic)) {
                        consumeTopicSendFraud(value);
                    }
                })
                .subscribe();
    }

    public void consumeTopicSendFraud(String message) {
        log.info("Inicio de consumo de topic {}", TOPIC_SEND_FRAUD);

        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            UUID transactionId = UUID.fromString(jsonNode.get("transactionId").asText());
            Double value = jsonNode.get("value").asDouble();
            String status = (value > AMOUNT_LIMIT) ? STATUS_RECHAZADO : STATUS_APROBADO;
            log.info("transactionId: {}, estado final: {}", transactionId, status);

            sendStatusToTransaction(transactionId, status);

        } catch (JsonProcessingException e) {
            log.error("Error de procesamiento JSON", e);
        } catch (Exception e) {
            log.error("Error al procesar el mensaje de fraude", e);
        }
    }

    private void sendStatusToTransaction(UUID transactionId, String status) {
        String message = "{\"transactionId\": \"" + transactionId
                + "\", \"status\": \"" + status + "\"}";
        log.info("Enviando mensaje a {}: {}", TOPIC_UPDATE_TRANSACTION_STATUS, message);

        kafkaSender.send(Mono.just(SenderRecord.create(
                new ProducerRecord<>(TOPIC_UPDATE_TRANSACTION_STATUS, message), null)))
                .doOnError(e ->
                        log.error("Error al enviar mensaje a {}", TOPIC_UPDATE_TRANSACTION_STATUS, e))
                .subscribe();
    }

}