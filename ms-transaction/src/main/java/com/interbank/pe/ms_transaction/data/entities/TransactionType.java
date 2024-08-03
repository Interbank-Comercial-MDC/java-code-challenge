package com.interbank.pe.ms_transaction.data.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("public.transaction_type")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class TransactionType {

    @Id
    private Integer id;

    private String name;

}
