package com.interbank.challenge.util;


public enum TransactionStatus {
    PENDIENTE("pendiente"),
    APROBADO("aprobado"),
    RECHAZADO("rechazado");

    private final String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static TransactionStatus fromString(String status) {
        for (TransactionStatus ts : TransactionStatus.values()) {
            if (ts.displayName.equalsIgnoreCase(status)) {
                return ts;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
