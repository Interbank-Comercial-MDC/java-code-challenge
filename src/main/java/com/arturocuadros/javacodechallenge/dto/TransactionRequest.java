package com.arturocuadros.javacodechallenge.dto;

import com.arturocuadros.javacodechallenge.model.TransferType;

public class TransactionRequest {
    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private int tranferTypeId;
    private double value;
    private TransferType transferType;

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

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }
}
