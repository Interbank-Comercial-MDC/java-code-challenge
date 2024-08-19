package com.lgutierrez.antifraudService.controller;

import com.lgutierrez.antifraudService.kafka.EventProducer;
import com.lgutierrez.transactionService.entity.Transaction;
import com.lgutierrez.transactionService.enums.TransactionStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/antifraud")
public class AntifraudController {

    private final EventProducer eventProducer;

    public AntifraudController(EventProducer eventProducer){
        this.eventProducer = eventProducer;
    }

    @PostMapping
    public String evaluateAntifraud(@RequestBody Transaction transaction) throws Exception {
        String msj = transaction.getAmount() > 1000 ? "REJECT" : "APPROVED";
        transaction.setStatus(TransactionStatus.valueOf(msj));

        eventProducer.sendMessage(transaction);

        return "Event handled successfully!";
    }
}
