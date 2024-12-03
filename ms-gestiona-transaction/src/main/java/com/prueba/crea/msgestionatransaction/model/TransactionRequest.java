package com.prueba.crea.msgestionatransaction.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequest {
    private Double amount;
    private int tipoTransaction;
}
