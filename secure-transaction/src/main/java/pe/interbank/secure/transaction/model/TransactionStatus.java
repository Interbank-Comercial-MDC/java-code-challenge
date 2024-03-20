package pe.interbank.secure.transaction.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class TransactionStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_status_id")
  private Integer transactionStatusId;

  @Column(nullable = false)
  private String name;
}
