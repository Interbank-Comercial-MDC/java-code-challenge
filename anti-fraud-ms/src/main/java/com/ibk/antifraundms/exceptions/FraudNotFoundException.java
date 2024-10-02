package com.ibk.antifraundms.exceptions;

public class FraudNotFoundException extends RuntimeException {
    public FraudNotFoundException(Long transactionId) {
        super("Fraud record not found for transaction ID: " + transactionId);
    }
}