package com.nttdata.controller.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonSerialize
public class TransactionReq {

    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private Integer transferTypeId;
    private BigDecimal value;
}
