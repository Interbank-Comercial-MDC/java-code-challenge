package com.prueba.crea.msgestionatransaction.util;

public enum StatusTransaction {

    PENDIENTE("Pendiente"),
    APROBADO("Aprobado"),
    RECHAZADO("Rechazado");

    private final String description;

    StatusTransaction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
