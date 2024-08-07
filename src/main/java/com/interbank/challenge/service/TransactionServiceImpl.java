package com.interbank.challenge.service;

import com.interbank.challenge.entity.TransactionDocument;
import com.interbank.challenge.entity.dto.request.TransactionRequest;
import com.interbank.challenge.entity.dto.response.TransactionResponse;
import com.interbank.challenge.entity.dto.response.TransactionType;
import com.interbank.challenge.entity.dto.response.TypeStatus;
import com.interbank.challenge.mapper.TransactionMapper;
import com.interbank.challenge.reporitory.TransactionRepository;
import com.interbank.challenge.util.TransactionStatus;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    private static final String TRANSACTION_CREATED_TOPIC = "transaction.created";
    private static final String TRANSACTION_STATUS_UPDATED_TOPIC = "transaction.status.updated";
    private final TransactionMapper transactionMapper = TransactionMapper.INSTANCE;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public Mono<TransactionRequest> createTransaction(TransactionRequest transactionRequest) {
        TransactionDocument transactionDocument = transactionMapper.toTransactionDocument(transactionRequest);
        transactionDocument.setTransactionStatus(TransactionStatus.PENDIENTE.toString());
        transactionDocument.setCreatedAt(LocalDate.now());
        return transactionRepository.save(transactionDocument)
                .doOnSuccess(transaction -> kafkaTemplate.send(TRANSACTION_CREATED_TOPIC, transaction.getId()))
                .map(transactionMapper::toTransactionRequest);
    }

    @Override
    @KafkaListener(topics = TRANSACTION_STATUS_UPDATED_TOPIC)
    public void updateTransactionStatus(ConsumerRecord<String, String> record) {
        String transactionId = record.key();
        String estado = record.value();
        TransactionStatus transactionStatus = TransactionStatus.fromString(estado);
        transactionRepository.findById(transactionId)
                .flatMap(transaction -> {
                    transaction.setTransactionStatus(transactionStatus.toString());
                    return transactionRepository.save(transaction);
                })
                .doOnError(error -> System.err.println("Error al actualizar la transacción: " + error.getMessage()))
                .doOnSuccess(transaction -> System.out.println("Transacción actualizada con éxito."))
                .subscribe();
    }

    @Override
    public Mono<TransactionResponse> getTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transaction -> {
                    TransactionResponse response = new TransactionResponse();
                    response.setTransactionId(transaction.getId());
                    response.setValue(transaction.getValue());
                    response.setDate(transaction.getCreatedAt().toString());

                    TransactionType type = new TransactionType();
                    type.setName(getTransactionTypeName(transaction.getTranferTypeId()));
                    response.setType(type);

                    TransactionStatus transactionStatus = TransactionStatus.fromString(transaction.getTransactionStatus());

                    TypeStatus typeStatus = new TypeStatus();
                    typeStatus.setName(transactionStatus.toString());
                    response.setStatus(typeStatus);

                    return response;
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Transacción no encontrada")));
    }

    private String getTransactionTypeName(Integer typeId) {
        switch (typeId) {
            case 1: return "debido";
            case 2: return "credito";
            default: return "Unknown";
        }
    }
}
