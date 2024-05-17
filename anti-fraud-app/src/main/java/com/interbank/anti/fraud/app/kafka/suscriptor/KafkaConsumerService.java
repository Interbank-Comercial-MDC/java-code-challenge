package com.interbank.anti.fraud.app.kafka.suscriptor;

import com.interbank.anti.fraud.core.service.AntiFraudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final AntiFraudService antiFraudService;

    @KafkaListener(topics = "fraud-topic", groupId = "group-prueba-fraude")
    public void listen(String message) {
        try {
            log.info("[ANTI-FRAUD-KAFKA] Recived init...");
            antiFraudService.validOperation(Long.parseLong(message));
            log.info("[ANTI-FRAUD-KAFKA] Recived end");
        } catch (Exception e) {
            log.error("[ANTI-FRAUD-KAFKA] message:{}, trace:{}", e.getMessage(), e);
        }
    }
}
