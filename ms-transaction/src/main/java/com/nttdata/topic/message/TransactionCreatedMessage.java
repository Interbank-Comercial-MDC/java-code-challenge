package com.nttdata.topic.message;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class TransactionCreatedMessage {

    private String transactionExternalId;
    private BigDecimal value;

}