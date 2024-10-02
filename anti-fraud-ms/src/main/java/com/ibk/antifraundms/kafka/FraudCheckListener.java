package com.ibk.antifraundms.kafka;

import com.ibk.antifraundms.dto.FraudCheckRequestDTO;
import com.ibk.antifraundms.kafka.producer.EventProducer;
import com.ibk.antifraundms.service.FraudCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FraudCheckListener {

    @Autowired
    private FraudCheckService fraudCheckService;

    @Autowired
    private EventProducer eventProducer;

    @KafkaListener(topics = "transactions.fraud-check", groupId = "anti-fraud-service")
    public void checkFraud(FraudCheckRequestDTO request) {
        String result = fraudCheckService.checkFraud(request);
        eventProducer.sendMessage(request.transactionId() + ":" + result);
    }
}
