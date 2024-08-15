package org.antifraude.adapters.in.rest;

import org.antifraude.ports.in.FinancialTransactionUseCase;
import org.antifraude.ports.in.KafkaUseCase;
import org.antifraude.domain.dto.FinancialTransactionDTO;
import org.antifraude.domain.model.FinancialTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;


@RestController
@RequestMapping("/api/financial-transaction")
public class FinancialTransactionController {

    private static final Logger logger = LoggerFactory.getLogger(FinancialTransactionController.class.getName());
    private final FinancialTransactionUseCase financialTransactionUseCase;
    private final KafkaUseCase kafkaUseCase;

    public FinancialTransactionController(FinancialTransactionUseCase financialTransactionUseCase, KafkaUseCase kafkaUseCase) {
        this.financialTransactionUseCase = financialTransactionUseCase;
        this.kafkaUseCase = kafkaUseCase;
    }

    @PostMapping
    public Mono<ResponseEntity<FinancialTransaction>> create(@RequestBody FinancialTransaction financialTransaction) {
        return financialTransactionUseCase.createFinancialTransaction(financialTransaction)
                .map(createdFinancial -> new ResponseEntity<>(createdFinancial, HttpStatus.CREATED))
                .doOnSuccess(createdFinancial ->
                        kafkaUseCase.sendMessage("antifraude-topic"
                                , String.valueOf(Objects.requireNonNull(createdFinancial.getBody()).getId())));
    }

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<FinancialTransactionDTO>> findID(@PathVariable("id") Long financialTransactionId) {
        logger.info("FinancialTransactionController findID {}", financialTransactionId);
        return financialTransactionUseCase.findFinancialTransactionByID(financialTransactionId)
                .map(createdFinancial -> new ResponseEntity<>(createdFinancial, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
