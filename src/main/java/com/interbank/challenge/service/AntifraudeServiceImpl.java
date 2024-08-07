package com.interbank.challenge.service;

import com.interbank.challenge.entity.TransactionDocument;
import com.interbank.challenge.reporitory.TransactionRepository;
import com.interbank.challenge.util.Constante;
import com.interbank.challenge.util.TransactionStatus;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AntifraudeServiceImpl implements AntifraudeService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final String TRANSACTION_CREATED_TOPIC = "transaction.created";
    private static final String TRANSACTION_STATUS_UPDATED_TOPIC = "transaction.status.updated";

    @Override
    @KafkaListener(topics = TRANSACTION_CREATED_TOPIC)
    public void listen(ConsumerRecord<String, String> record) {
        String transactionId = record.value();
        Mono<TransactionDocument> transactionMono = transactionRepository.findById(transactionId);

        transactionMono.subscribe(transaction -> {
            if (transaction != null) {
                if (transaction.getValue() > 1000) {
                    kafkaTemplate.send(TRANSACTION_STATUS_UPDATED_TOPIC, transactionId, TransactionStatus.RECHAZADO.toString());
                } else {
                    kafkaTemplate.send(TRANSACTION_STATUS_UPDATED_TOPIC, transactionId, TransactionStatus.APROBADO.toString());
                }
            } else {
                kafkaTemplate.send(TRANSACTION_STATUS_UPDATED_TOPIC, transactionId, Constante.TRANSACTION_FOUNT);
            }
        }, error -> {
            kafkaTemplate.send(TRANSACTION_STATUS_UPDATED_TOPIC, transactionId, Constante.TRANSACTION_ERROR);
        });
    }
}
