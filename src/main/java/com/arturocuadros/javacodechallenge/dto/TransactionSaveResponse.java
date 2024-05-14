package com.arturocuadros.javacodechallenge.dto;

import com.arturocuadros.javacodechallenge.model.TransferType;

public class TransactionSaveResponse {
    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private int tranferTypeId;
    private double value;

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
}
