package com.interbank.pe.ms_anti_fraud.service;

import com.interbank.pe.ms_anti_fraud.config.Properties;
import com.interbank.pe.ms_anti_fraud.models.thirdparty.transaction.PatchRequest;
import com.interbank.pe.ms_anti_fraud.models.event.TransactionEvent;
import com.interbank.pe.ms_anti_fraud.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AntiFraudService {

    private final Properties properties;
    private final WebClient webClient;

    @KafkaListener(topics = "transactionTopic")
    public void handleTransaction(TransactionEvent transactionEvent) {

        if (Double.compare(properties.getMaxValue(), transactionEvent.getValue()) < 0) {
            log.info("Value exceeds the maximum allowed!");
            log.info("Transaction Rejected");
            callTransactionService(transactionEvent.getTransactionId(), Constants.REJECTED_STATUS_CODE)
                    .subscribe();
        } else {
            log.info("Transaction Approved");
            callTransactionService(transactionEvent.getTransactionId(), Constants.APPROVED_STATUS_CODE)
                    .subscribe();
        }
    }

    public Mono<Void> callTransactionService(Integer transactionId, Integer statusCode){
        return webClient.patch()
                .uri("/" + transactionId)
                .body(Mono.just(new PatchRequest(statusCode)), PatchRequest.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
