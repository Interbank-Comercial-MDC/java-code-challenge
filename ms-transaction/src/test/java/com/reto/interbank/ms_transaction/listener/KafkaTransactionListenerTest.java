package com.reto.interbank.ms_transaction.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reto.interbank.ms_transaction.model.Transactions;
import com.reto.interbank.ms_transaction.repository.TransactionRepository;
import com.reto.interbank.ms_transaction.service.constans.ServiceConstans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class KafkaTransactionListenerTest {

    @Mock
    private KafkaReceiver<String, String> kafkaReceiver;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private KafkaTransactionListener kafkaTransactionListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ReceiverRecord<String, String> receiverRecord = mock(ReceiverRecord.class);
        when(receiverRecord.topic()).thenReturn(ServiceConstans.TOPIC_UPDATE_TRANSACTION_STATUS);
        when(receiverRecord.value()).thenReturn("{\"transactionId\":\"b32d0848-9321-4226-87c3-842bb4c05ad5\",\"status\":\"COMPLETED\"}");

        when(kafkaReceiver.receive()).thenReturn(Flux.just(receiverRecord));


    }

    @Test
    public void testConsumeTopicUpdateTransactionStatusSuccess() throws Exception {
        String message = "{\"transactionId\":\"b32d0848-9321-4226-87c3-842bb4c05ad5\",\"status\":\"COMPLETED\"}";
        JsonNode jsonNode = mock(JsonNode.class);
        JsonNode transactionIdNode = mock(JsonNode.class);
        JsonNode statusNode = mock(JsonNode.class);

        when(objectMapper.readTree(message)).thenReturn(jsonNode);
        when(jsonNode.get("transactionId")).thenReturn(transactionIdNode);
        when(transactionIdNode.asText()).thenReturn("b32d0848-9321-4226-87c3-842bb4c05ad5");
        when(jsonNode.get("status")).thenReturn(statusNode);
        when(statusNode.asText()).thenReturn("COMPLETED");

        Transactions transaction = new Transactions();
        transaction.setId(UUID.fromString("b32d0848-9321-4226-87c3-842bb4c05ad5"));
        transaction.setStatus("PENDING");

        when(transactionRepository.findById(any(UUID.class))).thenReturn(Mono.just(transaction));
        when(transactionRepository.save(any(Transactions.class))).thenReturn(Mono.just(transaction));

        kafkaTransactionListener.consumeTopicUpdateTransactionStatus(message);

        ArgumentCaptor<Transactions> transactionCaptor = ArgumentCaptor.forClass(Transactions.class);
        verify(transactionRepository).save(transactionCaptor.capture());

        assertEquals("COMPLETED", transactionCaptor.getValue().getStatus());
    }
}
