package com.ibk.antifraundms.dto;

import java.math.BigDecimal;

public record FraudCheckRequestDTO(Long transactionId, BigDecimal value) {}

