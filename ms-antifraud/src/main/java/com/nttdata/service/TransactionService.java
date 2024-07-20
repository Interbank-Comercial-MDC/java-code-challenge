package com.nttdata.service;

import com.nttdata.topic.client.TransactionProducer;
import com.nttdata.topic.message.TransactionCreatedMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final TransactionProducer transactionProducer;

    public TransactionService(TransactionProducer transactionProducer) {
        this.transactionProducer = transactionProducer;
    }

    public void validate(TransactionCreatedMessage transaction) {
        if (transaction.getValue().compareTo(BigDecimal.valueOf(1000)) > 0)
            transactionProducer.transaccionRejectedMessage(transaction.getTransactionExternalId());
        else
            transactionProducer.transactionApprovedMessage(transaction.getTransactionExternalId());

    }

}
