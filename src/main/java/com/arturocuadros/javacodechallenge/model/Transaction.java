package com.arturocuadros.javacodechallenge.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id")
    private String transactionExternalId;

    @Column(name = "account_external_id_debit")
    private String accountExternalIdDebit;

    @Column(name = "account_external_id_credit")
    private String accountExternalIdCredit;

    @Column(name = "tranfer_type_id")
    private int tranferTypeId;

    @Column
    private double value;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "transfer_type")
    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionExternalId() {
        return transactionExternalId;
    }

    public void setTransactionExternalId(String transactionExternalId) {
        this.transactionExternalId = transactionExternalId;
    }

    public String getAccountExternalIdDebit() {
        return accountExternalIdDebit;
    }

    public void setAccountExternalIdDebit(String accountExternalIdDebit) {
        this.accountExternalIdDebit = accountExternalIdDebit;
    }

    public String getAccountExternalIdCredit() {
        return accountExternalIdCredit;
    }

    public void setAccountExternalIdCredit(String accountExternalIdCredit) {
        this.accountExternalIdCredit = accountExternalIdCredit;
    }

    public int getTranferTypeId() {
        return tranferTypeId;
    }

    public void setTranferTypeId(int tranferTypeId) {
        this.tranferTypeId = tranferTypeId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
