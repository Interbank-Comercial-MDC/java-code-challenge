package com.challenge.transaction.consumer.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStateDTO {
  private Integer transactionId;

  @With
  private Integer transactionStatusId;
}
