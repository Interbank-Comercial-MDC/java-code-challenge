package com.interbank.challenge.service;

import com.interbank.challenge.entity.TransactionDocument;
import com.interbank.challenge.reporitory.TransactionRepository;
import com.interbank.challenge.util.TransactionStatus;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AntifraudeServiceImplTest {

    @InjectMocks
    private AntifraudeServiceImpl antifraudeService;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private TransactionRepository transactionRepository;

    private static final String TRANSACTION_CREATED_TOPIC = "transaction.created";
    private static final String TRANSACTION_STATUS_UPDATED_TOPIC = "transaction.status.updated";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Transaccion aprobada cuando el valor es menor a mil")
    void listen_ShouldApproveTransaction_WhenValueIsLessThanThreshold() {
        String transactionId = "123";
        ConsumerRecord<String, String> record = new ConsumerRecord<>(TRANSACTION_CREATED_TOPIC, 0, 0, null, transactionId);

        TransactionDocument transaction = new TransactionDocument();
        transaction.setValue(500);
        when(transactionRepository.findById(transactionId)).thenReturn(Mono.just(transaction));

        antifraudeService.listen(record);

        verify(kafkaTemplate, times(1))
                .send(eq(TRANSACTION_STATUS_UPDATED_TOPIC), eq(transactionId), eq(TransactionStatus.APROBADO.toString()));
    }

    @Test
    @DisplayName("Transaccion rechazada cuando el valor es mayor a mil")
    void listen_ShouldRejectTransaction_WhenValueIsGreaterThanThreshold() {
        String transactionId = "123";
        ConsumerRecord<String, String> record = new ConsumerRecord<>(TRANSACTION_CREATED_TOPIC, 0, 0, null, transactionId);

        TransactionDocument transaction = new TransactionDocument();
        transaction.setValue(1500);
        when(transactionRepository.findById(transactionId)).thenReturn(Mono.just(transaction));

        antifraudeService.listen(record);

        verify(kafkaTemplate, times(1))
                .send(eq(TRANSACTION_STATUS_UPDATED_TOPIC), eq(transactionId), eq(TransactionStatus.RECHAZADO.toString()));
    }

}