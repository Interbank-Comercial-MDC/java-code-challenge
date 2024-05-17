package com.interbank.anti.fraud.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class AntiFraudDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2934008372670552374L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private String tranferTypeId;
    private String value;
    private String status;
}
