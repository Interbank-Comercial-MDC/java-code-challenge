package com.interbank.pe.ms_transaction.models;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionStatusResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -7561361947455160066L;

    private String name;
}
