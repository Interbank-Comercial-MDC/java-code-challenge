package com.ibk.transactions.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record TransactionResponseDTO(
        Long transactionExternalId,
        int transactionType,
        String transactionStatus,
        BigDecimal value,
        Date createdAt,
        Date updatedAt
) {}

