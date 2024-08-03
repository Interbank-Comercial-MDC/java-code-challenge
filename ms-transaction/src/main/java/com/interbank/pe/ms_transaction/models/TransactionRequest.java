package com.interbank.pe.ms_transaction.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7821561615493564870L;

    @Schema(description = "accountExternalIdDebit", type = "String")
    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private Long tranferTypeId;
    private Long value;

}
