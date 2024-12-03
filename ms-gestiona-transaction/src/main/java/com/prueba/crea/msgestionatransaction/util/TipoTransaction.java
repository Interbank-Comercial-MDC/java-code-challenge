package com.prueba.crea.msgestionatransaction.util;

public enum TipoTransaction {
    CREDITO(1,"Credito"),
    DEBITO(2,"Debito");

    private final int codigo;
    private final String description;

    TipoTransaction(int codigo, String description) {
        this.codigo = codigo;
        this.description = description;
    }
    public int getCodigo() {
        return codigo;
    }

    public String getDescription() {
        return description;
    }


}
