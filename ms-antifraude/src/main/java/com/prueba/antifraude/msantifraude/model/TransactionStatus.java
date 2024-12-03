package com.prueba.antifraude.msantifraude.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionStatus {
    private String name;
}
