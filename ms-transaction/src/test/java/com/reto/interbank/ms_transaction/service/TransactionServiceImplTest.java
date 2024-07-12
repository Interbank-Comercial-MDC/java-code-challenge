package com.reto.interbank.ms_transaction.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.reto.interbank.ms_transaction.dto.TransactionDTO;
import com.reto.interbank.ms_transaction.model.Transactions;
import com.reto.interbank.ms_transaction.repository.TransactionRepository;
import com.reto.interbank.ms_transaction.service.impl.TransactionServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;

import reactor.test.StepVerifier;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private KafkaSender<String, String> kafkaSender;

    @InjectMocks
    private TransactionServiceImpl service;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreateTransactionSuccess() {
 
    	Transactions transaction = new Transactions();
        transaction.setId(UUID.randomUUID());
        transaction.setValue(100.0);
        transaction.setTransactionTypeId(1);

        when(repository.save(any(Transactions.class))).thenReturn(Mono.just(transaction));

        when(kafkaSender.send(any())).thenReturn(Flux.empty());

        Mono<Transactions> result = service.createTransaction(transaction);

        StepVerifier.create(result)
                .expectNext(transaction)
                .verifyComplete();

        verify(repository, times(1)).save(transaction);

        verify(kafkaSender, times(1)).send(any());
    }

    @Test
    public void testGetTransaction() {

    	UUID transactionId = UUID.randomUUID();
        Transactions transaction = new Transactions();
        transaction.setId(transactionId);
        transaction.setValue(200.0);

        when(repository.findById(transactionId)).thenReturn(Mono.just(transaction));

        Mono<TransactionDTO> result = service.getTransaction(transactionId);

        StepVerifier.create(result)
                .expectNextMatches(dto -> dto.getTransactionExternalId().equals(transactionId))
                .verifyComplete();
    }
    
    @Test
    public void testCreateTransactionRepositoryError() {

    	Transactions transaction = new Transactions();
        transaction.setId(UUID.randomUUID());
        transaction.setValue(100.0);
        transaction.setTransactionTypeId(1);

        when(repository.save(any(Transactions.class))).thenReturn(Mono.error(new RuntimeException("Error al guardar")));

        Mono<Transactions> result = service.createTransaction(transaction);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(repository, times(1)).save(transaction);

        verify(kafkaSender, never()).send(any());
    }
    

}