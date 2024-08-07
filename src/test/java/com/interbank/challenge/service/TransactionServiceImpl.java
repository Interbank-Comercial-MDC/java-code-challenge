package com.interbank.challenge.service;

import com.interbank.challenge.entity.TransactionDocument;
import com.interbank.challenge.entity.dto.request.TransactionRequest;
import com.interbank.challenge.entity.dto.response.TransactionResponse;
import com.interbank.challenge.mapper.TransactionMapper;
import com.interbank.challenge.reporitory.TransactionRepository;
import com.interbank.challenge.util.TransactionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private final TransactionMapper transactionMapper = TransactionMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Crea una Transaccion - retorna mensage exitoso")
    void createTransaction_ShouldReturnTransactionRequest() {
        TransactionRequest request = new TransactionRequest();

        TransactionDocument document = transactionMapper.toTransactionDocument(request);
        when(transactionRepository.save(any(TransactionDocument.class)))
                .thenReturn(Mono.just(document));

        TransactionRequest result = transactionService.createTransaction(request).block();

        assertEquals(request, result);
    }

    @Test
    @DisplayName("obtiene una transaccion por ID")
    void getTransactionById_ShouldReturnTransactionResponse() {

        TransactionDocument transactionDocument = new TransactionDocument();
        transactionDocument.setId("testTransactionId");
        transactionDocument.setValue(1500);
        transactionDocument.setTranferTypeId(1);
        transactionDocument.setTransactionStatus(TransactionStatus.PENDIENTE.toString());
        transactionDocument.setCreatedAt(LocalDate.of(2024, 8, 6));

        when(transactionRepository.findById("testTransactionId")).thenReturn(Mono.just(transactionDocument));

        Mono<TransactionResponse> transactionResponseMono = transactionService.getTransactionById("testTransactionId");;

        TransactionResponse transactionResponse = transactionResponseMono.block();
        assertNotNull(transactionResponse);
        assertEquals("testTransactionId", transactionResponse.getTransactionId());
        assertEquals(1500, transactionResponse.getValue());
        assertEquals("2024-08-06", transactionResponse.getDate());
        assertEquals("debido", transactionResponse.getType().getName());
        assertEquals(TransactionStatus.PENDIENTE.toString(), transactionResponse.getStatus().getName());
    }

}