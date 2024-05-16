package com.java.code.challenge.transaction.validation.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import com.java.code.challenge.transaction.validation.api.dto.TransactionRequest;
import com.java.code.challenge.transaction.validation.api.dto.TransactionResponse;
import com.java.code.challenge.transaction.validation.api.exception.TransactionNotFoundException;
import com.java.code.challenge.transaction.validation.api.model.Transaction;
import com.java.code.challenge.transaction.validation.api.repository.TransactionRepository;
import com.java.code.challenge.transaction.validation.api.service.impl.TransactionServiceImpl;
import com.java.code.challenge.transaction.validation.api.util.Constantes;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private KafkaTemplate<String, Transaction> kafkaTemplate;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	private Transaction transaction;
	private TransactionRequest transactionRequest;
	private UUID accountExternalId;

	@BeforeEach
	void setUp() {
		transaction = new Transaction();
		transaction.setId(1L);
		transaction.setAccountExternalIdDebit(UUID.randomUUID());
		transaction.setAccountExternalIdCredit(UUID.randomUUID());
		transaction.setTranType("Agente");
		transaction.setValue(999.0);
		transaction.setStatus("Aprobado");

		transactionRequest = new TransactionRequest();
		transactionRequest.setAccountExternalIdDebit(UUID.randomUUID());
		transactionRequest.setAccountExternalIdCredit(UUID.randomUUID());
		transactionRequest.setTranType("Agente");
		transactionRequest.setValue(999.0);
		transactionRequest.setStatus("");

		accountExternalId = UUID.randomUUID();
	}

	@Test
	void testCreateTransactionWithNegativeValue() {
		transactionRequest.setValue(-100.0);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> transactionService.createTransaction(transactionRequest),
				"Se esperaba que createTransaction() lanzara una excepción");

		assertTrue(thrown.getMessage().contains(Constantes.EX_NEGATIVO));
		verify(kafkaTemplate, times(0)).send(anyString(), any(Transaction.class));
		verify(transactionRepository, times(0)).save(any(Transaction.class));
	}

	@Test
    void testGetAllTransactions() {
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction));

        List<Transaction> result = transactionService.getAllTransactions();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(transactionRepository, times(1)).findAll();
    }

	@Test
    void testGetTransactionsByAccountExternalId() {
        when(transactionRepository.findByAccountExternalId(any(UUID.class)))
                .thenReturn(Arrays.asList(transaction));

        List<TransactionResponse> result = transactionService.getTransactionsByAccountExternalId(accountExternalId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(transactionRepository, times(1)).findByAccountExternalId(accountExternalId);
    }

	@Test
    void testGetTransactionsByAccountExternalIdNotFound() {
        when(transactionRepository.findByAccountExternalId(any(UUID.class)))
                .thenReturn(Arrays.asList());

        TransactionNotFoundException thrown = assertThrows(
            TransactionNotFoundException.class,
            () -> transactionService.getTransactionsByAccountExternalId(accountExternalId),
            "Se esperaba que getTransactionsByAccountExternalId() lanzara una excepción"
        );

        assertTrue(thrown.getMessage().contains("No se encontraron transacciones"));
        verify(transactionRepository, times(1)).findByAccountExternalId(accountExternalId);
    }
}
