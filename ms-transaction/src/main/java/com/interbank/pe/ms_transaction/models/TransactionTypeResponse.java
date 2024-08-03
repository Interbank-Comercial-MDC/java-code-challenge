package com.interbank.pe.ms_transaction.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionTypeResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5972072251652506821L;

    private String name;

}
