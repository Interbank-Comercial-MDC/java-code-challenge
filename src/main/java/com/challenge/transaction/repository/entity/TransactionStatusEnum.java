package com.challenge.transaction.repository.entity;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatusEnum {
  PENDING(0, "Pending"),
  APPROVED(1, "Approved"),
  REJECTED(2, "Rejected");

  final Integer code;
  final String description;

  public static TransactionStatusEnum fromCode(Integer code) {
    return Stream.of(TransactionStatusEnum.values())
        .filter(eventType -> eventType.getCode().equals(code))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Not Found TransactionStatus: " + code));
  }
}
