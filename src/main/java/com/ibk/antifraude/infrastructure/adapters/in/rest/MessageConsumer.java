package com.ibk.antifraude.infrastructure.adapters.in.rest;

import com.ibk.antifraude.application.ports.in.FinancialTransactionUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
public class MessageConsumer {

    private final FinancialTransactionUseCase financialTransactionUseCase;

    @KafkaListener(topics = "antifraude-topic", groupId = "antifraudeGroup")
    public Mono<Void> listen(String message) {
        return financialTransactionUseCase.updateFinancialTransactionByID(Long.valueOf(message));
    }
}
