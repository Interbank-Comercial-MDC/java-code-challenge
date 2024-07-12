package com.reto.interbank.ms_transaction.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reto.interbank.ms_transaction.repository.TransactionRepository;
import com.reto.interbank.ms_transaction.service.constans.ServiceConstans;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;

@Slf4j
@Component
public class KafkaTransactionListener {

	private final ObjectMapper objectMapper;
    private final KafkaReceiver<String, String> kafkaReceiver;
    
    private TransactionRepository transactionRepository;

	
    public KafkaTransactionListener(KafkaReceiver<String, String> kafkaReceiver,
            ObjectMapper objectMapper, KafkaSender<String, String> kafkaSender, 
            TransactionRepository transactionRepository) {
    	this.kafkaReceiver = kafkaReceiver;
    	this.objectMapper = objectMapper;
    	this.transactionRepository = transactionRepository;
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
	
	            if (ServiceConstans.TOPIC_UPDATE_TRANSACTION_STATUS.equals(topic)) {
	            	consumeTopicUpdateTransactionStatus(value);
	            }
	        })
	        .subscribe();
    }
    
    public void consumeTopicUpdateTransactionStatus(String message) {
        log.info("Inicio de consumo de topic "+ServiceConstans.TOPIC_UPDATE_TRANSACTION_STATUS );
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            UUID transactionId = UUID.fromString(jsonNode.get("transactionId").asText());
            String status = jsonNode.get("status").asText();
            log.info("Recibido transactionId: {}, nuevo estado: {}", transactionId, status);
            
            updateTransactionStatus(transactionId, status);

        } catch (JsonMappingException e) {
            log.error("Error de mapeo JSON", e);
        } catch (JsonProcessingException e) {
            log.error("Error de procesamiento JSON", e);
        }
    }
    
	
	private void updateTransactionStatus(UUID transactionId, String status) {
        transactionRepository.findById(transactionId)
            .flatMap(transaction -> {
                transaction.setStatus(status);
                return transactionRepository.save(transaction);
            })
            .doOnSuccess(updatedTransaction -> 
                log.info("Estado de la transacción {} actualizado a {}", updatedTransaction.getId(), updatedTransaction.getStatus()))
            .doOnError(e -> 
                log.error("Error actualizando el estado de la transacción", e))
            .subscribe();
    }
	


}