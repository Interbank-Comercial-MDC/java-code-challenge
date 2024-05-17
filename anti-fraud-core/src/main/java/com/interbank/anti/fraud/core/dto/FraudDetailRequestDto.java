package com.interbank.anti.fraud.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FraudDetailRequestDto implements Serializable {
    private static final long serialVersionUID = -2441982926762095330L;

    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private Integer tranferTypeId;
    private Integer value;

}
