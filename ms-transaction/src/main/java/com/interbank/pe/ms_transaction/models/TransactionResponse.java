package com.interbank.pe.ms_transaction.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -6469297446208845478L;

    private Integer transactionExternalId;
    private TransactionTypeResponse transactionTypeResponse;
    private TransactionStatusResponse transactionStatusResponse;
    private Double value;
    private Date createdAt;

}
