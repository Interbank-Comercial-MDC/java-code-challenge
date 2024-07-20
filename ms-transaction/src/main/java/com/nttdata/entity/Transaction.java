package com.nttdata.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "account_external_id_debit")
    private String accountExternalIdDebit;

    @Column(name = "account_external_id_credit")
    private String accountExternalIdCredit;

    @Column(name = "transfer_type_id")
    private int transferTypeId;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}