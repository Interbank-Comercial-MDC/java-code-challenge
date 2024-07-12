package com.reto.interbank.ms_transaction.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table("transactions")
public class Transactions {

	@Id
    private UUID id;
    private UUID accountExternalIdDebit;
    private UUID accountExternalIdCredit;
    private int transactionTypeId;
    private Double value;
    private String status;
    private LocalDateTime createdAt;

}