package pe.interbank.secure.transaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer statusId;

  @Column(nullable = false, unique = true)
  private String name;
}
