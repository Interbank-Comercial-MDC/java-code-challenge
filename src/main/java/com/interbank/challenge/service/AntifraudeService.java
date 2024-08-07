package com.interbank.challenge.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface AntifraudeService {

    void listen(ConsumerRecord<String, String> record);
}
