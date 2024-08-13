package com.hexagonal.demoAntiFraud.infrastructure.entity.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionDTO {

    private UUID transactionExternalId;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private double value;
    private LocalDateTime createdAt;

    @Setter
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TransactionType {
        private String name;

        public static TransactionType of(String name) {
            return new TransactionType(name);
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TransactionStatus {
        private String name;

        public static TransactionStatus of(String name) {
            return new TransactionStatus(name);
        }
    }

}
