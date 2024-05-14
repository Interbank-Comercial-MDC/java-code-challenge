package com.arturocuadros.javacodechallenge.dto;

import com.arturocuadros.javacodechallenge.model.TransactionStatus;
import com.arturocuadros.javacodechallenge.model.TransferType;

import java.util.Date;

public class TransactionUpdateResponse {
    private String transactionExternalId;
    private TransferType transferType;
    private TransactionStatus transactionStatus;
    private double value;
    private Date createdAt;

    public String getTransactionExternalId() {
        return transactionExternalId;
    }

    public void setTransactionExternalId(String transactionExternalId) {
        this.transactionExternalId = transactionExternalId;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
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
}
