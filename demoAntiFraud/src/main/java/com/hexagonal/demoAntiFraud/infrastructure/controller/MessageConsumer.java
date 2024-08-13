package com.hexagonal.demoAntiFraud.infrastructure.controller;

import com.hexagonal.demoAntiFraud.domain.ports.in.TransactionUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import static com.hexagonal.demoAntiFraud.infrastructure.util.Constants.*;

@Slf4j
@Component
@AllArgsConstructor
public class MessageConsumer {
    private final TransactionUseCase transactionUseCase;

    @KafkaListener(topics = TOPIC, groupId = GROUPID)
    public Mono<Void> listen(String message) {
        return transactionUseCase.updateTransactionByID(Long.valueOf(message));
    }
}
