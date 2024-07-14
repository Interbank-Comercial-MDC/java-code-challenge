package com.challenge.transaction.repository.entity;

import com.challenge.transaction.util.DateUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer transactionExternalId;

  @Column(name = "account_external_id_debit")
  private Integer accountExternalIdDebit;

  @Column(name = "account_external_id_credit")
  private Integer accountExternalIdCredit;

  @Column(name = "tranfer_type_id")
  private Integer tranferTypeId;

  @Column(name = "value", nullable = false)
  private BigDecimal value;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "transaction_status_id", nullable = false)
  private Integer transactionStatusId;

  @PrePersist
  private void preInsert() {
    this.createdAt = DateUtils.getDateTimeLimaZone();
    this.transactionStatusId = TransactionStatusEnum.PENDING.code;
  }
}
