package com.ibk.antifraundms.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topicFraudCheckResult() {
        return new NewTopic("transactions.fraud-check.result", 1, (short) 1);
    }

    @Bean
    public NewTopic topicTransactions() {
        return new NewTopic("transactions.fraud-check", 1, (short) 1);
    }
}