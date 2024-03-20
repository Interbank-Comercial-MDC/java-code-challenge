package pe.interbank.secure.transaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

  //@Id
  //@GeneratedValue(strategy = GenerationType.UUID)
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID accountExternalId;

  @Column(nullable = false, length = 34)
  private String accountNumber;
}
