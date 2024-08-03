package com.interbank.pe.ms_transaction.data.entities;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("public.transaction")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Transaction {

    @Id
    private Integer id;

    private Integer typeCode;
    private Integer statusCode;
    private Double value;
    private LocalDate createDate;
    private String accountExternalIdDebit;
    private String accountExternalIdCredit;

}
