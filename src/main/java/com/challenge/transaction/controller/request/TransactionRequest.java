package com.challenge.transaction.controller.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
  private Integer accountExternalIdDebit;
  private Integer accountExternalIdCredit;
  private Integer tranferTypeId;

  @NotNull private BigDecimal value;
}
