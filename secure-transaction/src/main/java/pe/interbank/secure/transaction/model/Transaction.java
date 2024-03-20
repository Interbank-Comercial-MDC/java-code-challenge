package pe.interbank.secure.transaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID transactionExternalId;

  @ManyToOne
  @JoinColumn(name = "debit_account_id", nullable = false)
  private Account debitAccount;

  @ManyToOne
  @JoinColumn(name = "credit_account_id", nullable = false)
  private Account creditAccount;

  @Column(nullable = false)
  private BigDecimal value;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "status_id", nullable = false)
  private TransactionStatus transactionStatus;

  @ManyToOne
  @JoinColumn(name = "type_id", nullable = false)
  private TransactionType transactionType;

  @ManyToOne
  @JoinColumn(name = "transfer_type_id", nullable = false)
  private TransferType transferType;
}
