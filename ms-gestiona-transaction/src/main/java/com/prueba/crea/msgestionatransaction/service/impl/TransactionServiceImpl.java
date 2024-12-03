package com.prueba.crea.msgestionatransaction.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.crea.msgestionatransaction.Kafka.KafkaConsumer;
import com.prueba.crea.msgestionatransaction.model.Transaction;
import com.prueba.crea.msgestionatransaction.model.TransactionRequest;
import com.prueba.crea.msgestionatransaction.model.TransactionStatusUpdate;
import com.prueba.crea.msgestionatransaction.repository.TransactionRepository;
import com.prueba.crea.msgestionatransaction.service.TransactionService;
import com.prueba.crea.msgestionatransaction.util.Constante;
import com.prueba.crea.msgestionatransaction.util.StatusTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transacRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    public TransactionServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Mono<Transaction> sendTransaction(TransactionRequest tx) {
        JsonNode jsonNode = null;

        Transaction msjTransaction = Transaction.builder()
                .accountExternalId(UUID.randomUUID().toString())
                .tranferTypeId(tx.getTipoTransaction())
                .status(StatusTransaction.PENDIENTE.getDescription())
                .value(tx.getAmount())
                .createdAt(new Date())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            ObjectMapper mapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(msjTransaction);
            jsonNode = mapper.readTree(json);

            transacRepository.save(msjTransaction);
            logger.info("TRANSACTION CREADA: " + msjTransaction);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonNode != null) kafkaTemplate.send(Constante.TOPIC_TRANSACTION_NEW, jsonNode.toString());
        logger.info("TRANSACTION ENVIADA: " + jsonNode.toString());
        return Mono.just(msjTransaction);
    }

    @Override
    public Mono<Void> updateTransaction(TransactionStatusUpdate txActualiza) {
        transacRepository.findById(txActualiza.getTransactionExternalId())
                .ifPresent(tx -> {
                    tx.setStatus(txActualiza.getTransactionStatus().getName());
                    tx.setUpdateAt(new Date());
                    transacRepository.save(tx);
                    logger.info("TRANSACTION ACTUALIZADA: " + tx);
                });

        return null;
    }
}
