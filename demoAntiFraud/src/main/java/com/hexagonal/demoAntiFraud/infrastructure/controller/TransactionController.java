package com.hexagonal.demoAntiFraud.infrastructure.controller;

import com.hexagonal.demoAntiFraud.domain.models.RequestTransaction;
import com.hexagonal.demoAntiFraud.domain.ports.in.KafkaUseCase;
import com.hexagonal.demoAntiFraud.domain.ports.in.TransactionUseCase;
import com.hexagonal.demoAntiFraud.infrastructure.entity.dto.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Objects;

import static com.hexagonal.demoAntiFraud.infrastructure.util.Constants.TOPIC;

@RestController
@RequestMapping("/v1/transactions")
@Tag(name = "TRANSACTION", description = "Operations defined for the Financial Transaction project")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class.getName());
    private final TransactionUseCase transactionUseCase;
    private final KafkaUseCase kafkaUseCase;

    public TransactionController(TransactionUseCase transactionUseCase, KafkaUseCase kafkaUseCase) {
        this.transactionUseCase = transactionUseCase;
        this.kafkaUseCase = kafkaUseCase;
    }
    @Operation(summary = "Create a new Transaction")
    @ApiResponse(responseCode = "201", description = "TRANSACTION created successfully")
    @PostMapping
    public Mono<ResponseEntity<RequestTransaction>> create(@RequestBody RequestTransaction payloadTransaction) {
        return transactionUseCase.createTransaction(payloadTransaction)
                .map(createdTransaction -> new ResponseEntity<>(createdTransaction, HttpStatus.CREATED))
                .doOnSuccess(createdTransaction ->
                        kafkaUseCase.sendMessage(TOPIC
                                , String.valueOf(Objects.requireNonNull(createdTransaction.getBody()).getId())));
    }

    @Operation(summary = "obtain a Transaction by its UUID")
    @GetMapping(value = "/transaction/{id}")
    public Mono<ResponseEntity<TransactionDTO>> findID(@PathVariable("id") Long transactionId) {
        logger.info("TransactionController findID {}", transactionId);
        return transactionUseCase.findTransactionByID(transactionId)
                .map(createdTransaction -> new ResponseEntity<>(createdTransaction, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
