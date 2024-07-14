package com.challenge.transaction.repository.entity;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionTypeEnum {
  DEPOSIT(0, "Deposit"),
  WITHDRAWAL(1, "Withdrawal");

  final Integer code;
  final String description;

  public static TransactionTypeEnum fromCode(Integer code) {
    return Stream.of(TransactionTypeEnum.values())
        .filter(eventType -> eventType.getCode().equals(code))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Not Found TransactionType: " + code));
  }
}
