package org.antifraude.adapters.in.rest;

import org.antifraude.ports.in.FinancialTransactionUseCase;
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

    @KafkaListener(topics = "antifraude-topic", groupId = "antifraude")
    public Mono<Void> listen(String message) {
        return financialTransactionUseCase.updateFinancialTransactionByID(Long.valueOf(message));
    }
}
