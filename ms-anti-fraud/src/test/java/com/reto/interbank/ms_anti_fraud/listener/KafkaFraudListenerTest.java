package com.reto.interbank.ms_anti_fraud.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class KafkaFraudListenerTest {

    @Mock
    private KafkaReceiver<String, String> kafkaReceiver;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private KafkaSender<String, String> kafkaSender;

    @InjectMocks
    private KafkaFraudListener kafkaFraudListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsumeTopicSendFraudApproved() throws Exception {
        String message = "{\"transactionId\":\"b32d0848-9321-4226-87c3-842bb4c05ad5\",\"value\":500}";
        JsonNode jsonNode = mock(JsonNode.class);
        JsonNode transactionIdNode = mock(JsonNode.class);
        JsonNode valueNode = mock(JsonNode.class);

        when(objectMapper.readTree(message)).thenReturn(jsonNode);
        when(jsonNode.get("transactionId")).thenReturn(transactionIdNode);
        when(transactionIdNode.asText()).thenReturn("b32d0848-9321-4226-87c3-842bb4c05ad5");
        when(jsonNode.get("value")).thenReturn(valueNode);
        when(valueNode.asDouble()).thenReturn(500.0);

        // Simular que el método send retorna un Mono vacío
        when(kafkaSender.send(any())).thenReturn(Flux.empty());

        kafkaFraudListener.consumeTopicSendFraud(message);

        verify(kafkaSender).send(any());

    }

    @Test
    public void testConsumeTopicSendFraudRejected() throws Exception {
        String message = "{\"transactionId\":\"b32d0848-9321-4226-87c3-842bb4c05ad5\",\"value\":1500}";
        JsonNode jsonNode = mock(JsonNode.class);
        JsonNode transactionIdNode = mock(JsonNode.class);
        JsonNode valueNode = mock(JsonNode.class);

        when(objectMapper.readTree(message)).thenReturn(jsonNode);
        when(jsonNode.get("transactionId")).thenReturn(transactionIdNode);
        when(transactionIdNode.asText()).thenReturn("b32d0848-9321-4226-87c3-842bb4c05ad5");
        when(jsonNode.get("value")).thenReturn(valueNode);
        when(valueNode.asDouble()).thenReturn(1500.0);

        when(kafkaSender.send(any())).thenReturn(Flux.empty());

        kafkaFraudListener.consumeTopicSendFraud(message);

        verify(kafkaSender).send(any());

    }

    @Test
    public void testConsumeTopicSendFraudJsonProcessingException() throws Exception {
        String message = "{\"transactionId\":\"invalid-uuid\",\"value\":500}";

        when(objectMapper.readTree(message)).thenThrow(new JsonProcessingException("Error") {});

        kafkaFraudListener.consumeTopicSendFraud(message);
        // Verifica que no se envió ningún mensaje
        verify(kafkaSender, never()).send(any());
    }



    private JsonNode mockJsonNode(Object value) {
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.asText()).thenReturn(value.toString());
        when(jsonNode.asDouble()).thenReturn(value instanceof Double ? (Double) value : 0.0);
        return jsonNode;
    }
}
