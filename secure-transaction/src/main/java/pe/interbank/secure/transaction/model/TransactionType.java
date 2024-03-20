package pe.interbank.secure.transaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer typeId;

  @Column(nullable = false, unique = true)
  private String name;
}
