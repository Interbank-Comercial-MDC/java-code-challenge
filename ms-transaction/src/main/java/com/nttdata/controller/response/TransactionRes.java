package com.nttdata.controller.response;

import com.nttdata.entity.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionRes {

    private String transactionExternalId;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private BigDecimal value;
    private LocalDateTime createdAt;

    @Builder
    @Data
    public static class TransactionType {
        private String name;
    }

    @Builder
    @Data
    public static class TransactionStatus {
        private String name;
    }
}
