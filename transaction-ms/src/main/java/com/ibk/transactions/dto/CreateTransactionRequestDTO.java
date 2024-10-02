package com.ibk.transactions.dto;

import java.math.BigDecimal;

public record CreateTransactionRequestDTO(
        String accountExternalIdDebit,
        String accountExternalIdCredit,
        int transferTypeId,
        BigDecimal value
) {}

