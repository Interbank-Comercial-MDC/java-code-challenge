package com.interbank.pe.ms_transaction.controller;

import com.interbank.pe.ms_transaction.models.PatchRequest;
import com.interbank.pe.ms_transaction.models.TransactionRequest;
import com.interbank.pe.ms_transaction.models.TransactionResponse;
import com.interbank.pe.ms_transaction.service.TransactionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@Slf4j
public class TransactionController {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;

    @PostConstruct
    public void logKafkaConfig() {
        System.out.println("Kafka bootstrap servers: " + kafkaBootstrapServers);
    }

    private final TransactionService transactionService;

    @PostMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public Mono<Void> transaction(@RequestBody TransactionRequest transactionRequest){
        return transactionService.transaction(transactionRequest);
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public Flux<TransactionResponse> getTransactions() {
        return transactionService.getTransactions();
    }

    @PatchMapping(value = "/{transactionId}", produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public Mono<Void> patchTransaction(@PathVariable Integer transactionId, @RequestBody PatchRequest patchRequest) {
        return transactionService.patchTransaction(transactionId, patchRequest);
    }

    @GetMapping(value = "/{transactionId}", produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public Mono<TransactionResponse> getTransactionById(@PathVariable Integer transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

}
