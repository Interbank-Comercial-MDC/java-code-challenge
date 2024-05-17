package com.interbank.anti.fraud.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Transaction extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2306754104177671615L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_external_debit_id", nullable = false, unique = true)
    private String accountExternalIdDebit;

    @Column(name = "transaction_external_credit_id", nullable = false, unique = true)
    private String accountExternalIdCredit;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "transaction_status_id", nullable = false)
    private TransactionStatus transactionStatus;

    private String value;

}
