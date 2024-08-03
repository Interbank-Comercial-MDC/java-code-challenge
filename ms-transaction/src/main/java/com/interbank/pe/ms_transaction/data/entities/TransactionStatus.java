package com.interbank.pe.ms_transaction.data.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("public.transaction_status")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class TransactionStatus {

    @Id
    private Integer id;

    private String name;

}
