package com.interbank.challenge.controller;

import com.interbank.challenge.entity.dto.request.TransactionRequest;
import com.interbank.challenge.entity.dto.response.TransactionResponse;
import com.interbank.challenge.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Crea una Transaccion - retorna mensage exitoso")
    void createTransaction_ShouldReturnSuccessMessage() {
        TransactionRequest transactionRequest = new TransactionRequest();

        when(transactionService.createTransaction(any(TransactionRequest.class)))
                .thenReturn(Mono.just(transactionRequest));

        ResponseEntity<Map<String, String>> responseEntity = transactionController
                .createTransaction("Bearer token", transactionRequest)
                .block();

        assertEquals("El registro se hizo correctamente", responseEntity.getBody().get("message"));
    }

    @Test
    @DisplayName("obtiene una transaccion")
    void getTransaction_ShouldReturnTransactionResponse() {
        String transactionId = "123";
        TransactionResponse response = new TransactionResponse();

        when(transactionService.getTransactionById(transactionId))
                .thenReturn(Mono.just(response));

        TransactionResponse result = transactionController.getTransaction("Bearer token", transactionId).block();

        assertEquals(response, result);
    }
}
