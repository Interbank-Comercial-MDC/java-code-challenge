package com.challenge.transaction.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
  private Integer transactionExternalId;
  private Integer accountExternalIdDebit;
  private Integer accountExternalIdCredit;
  private Integer tranferTypeId;
  private Integer transactionStatusId;
  private BigDecimal value;
  private String createdAt;
}
