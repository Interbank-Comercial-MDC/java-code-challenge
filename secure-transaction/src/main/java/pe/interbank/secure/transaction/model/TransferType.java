package pe.interbank.secure.transaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransferType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer transferTypeId;

  @Column(nullable = false, unique = true)
  private String name;
}
