package pe.interbank.secure.transaction.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Transaction {

  @Id
  @Column(name = "transaction_external_id")
  private UUID transactionExternalId;

  @ManyToOne
  @JoinColumn(name = "debit_account_id", nullable = false)
  private Account debitAccount;

  @ManyToOne
  @JoinColumn(name = "credit_account_id", nullable = false)
  private Account creditAccount;

  @Column(nullable = false)
  private BigDecimal value;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "transaction_status_id", nullable = false)
  private TransactionStatus transactionStatus;

  @ManyToOne
  @JoinColumn(name = "transaction_type_id", nullable = false)
  private TransactionType transactionType;

  @ManyToOne
  @JoinColumn(name = "transfer_type_id", nullable = false)
  private TransferType transferType;
}
