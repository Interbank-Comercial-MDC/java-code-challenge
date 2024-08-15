package org.antifraude.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancialTransaction {
    @Id
    private Long id;
    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private int transactionStatusId;
    private UUID transactionExternalId;
    private LocalDateTime createdAt;
    private int transferTypeId;
    private int value;
}

