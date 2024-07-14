package com.challenge.transaction.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionResponse {
  Integer transactionExternalId;
  Integer accountExternalIdDebit;
  Integer accountExternalIdCredit;
  DetailName transactionType;
  DetailName transactionStatus;
  BigDecimal value;
  String createdAt;
}
