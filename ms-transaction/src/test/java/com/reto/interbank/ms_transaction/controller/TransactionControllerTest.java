package com.reto.interbank.ms_transaction.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.reto.interbank.ms_transaction.dto.TransactionDTO;
import com.reto.interbank.ms_transaction.model.Transactions;
import com.reto.interbank.ms_transaction.service.TransactionService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class TransactionControllerTests {

    @Mock
    private TransactionService service;

    @InjectMocks
    private TransactionController controller;
    
    private static final String STATUS_PENDIENTE = "PENDIENTE";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransactionValidTransactionReturnsOkResponse() {
        
        Transactions mockTransaction = new Transactions();
        mockTransaction.setId(UUID.randomUUID());
        mockTransaction.setStatus(STATUS_PENDIENTE);
        mockTransaction.setCreatedAt(LocalDateTime.now());

        when(service.createTransaction(any(Transactions.class))).thenReturn(Mono.just(mockTransaction));

        
        Mono<ResponseEntity<Transactions>> responseMono = controller.createTransaction(mockTransaction);

        StepVerifier.create(responseMono)
                .assertNext(responseEntity -> {
                    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(responseEntity.getBody()).isEqualTo(mockTransaction);
                })
                .verifyComplete();

        verify(service, times(1)).createTransaction(any(Transactions.class));
    }

    @Test
    void getTransactionByIdValidIdReturnsOkResponse() {
        UUID transactionId = UUID.randomUUID();
        TransactionDTO mockTransactionDTO = new TransactionDTO();
        mockTransactionDTO.setTransactionExternalId(transactionId);
        mockTransactionDTO.setValue(100.0);
        mockTransactionDTO.setCreatedAt(LocalDateTime.now());

        when(service.getTransaction(transactionId)).thenReturn(Mono.just(mockTransactionDTO));

        Mono<ResponseEntity<TransactionDTO>> responseMono = controller.getTransactionById(transactionId);

        StepVerifier.create(responseMono)
                .assertNext(responseEntity -> {
                    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(responseEntity.getBody()).isEqualTo(mockTransactionDTO);
                })
                .verifyComplete();

        verify(service, times(1)).getTransaction(transactionId);
    }
}