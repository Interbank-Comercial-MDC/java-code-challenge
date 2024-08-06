package com.interbank.challenge.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Document(collection = "table_transaction")
@Data
@NoArgsConstructor
public class TransactionDocument {

    public TransactionDocument(String accountExternalIdDebit, String accountExternalIdCredit, Integer tranferTypeId, Integer value, String transactionStatus, LocalDate createdAt) {
        this.accountExternalIdDebit = accountExternalIdDebit;
        this.accountExternalIdCredit = accountExternalIdCredit;
        this.tranferTypeId = tranferTypeId;
        this.value = value;
        this.transactionStatus = transactionStatus;
        this.createdAt = createdAt;
    }

    @Id
    private String id;

    private String accountExternalIdDebit;

    private String accountExternalIdCredit;

    private Integer tranferTypeId;

    private Integer value;

    private String transactionStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

}
