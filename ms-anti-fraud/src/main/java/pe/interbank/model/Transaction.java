package pe.interbank.model;

import lombok.Data;

import java.util.Date;

@Data
public class Transaction {

  private String accountExternalId;
  private int tranferTypeId;
  private Double value;
  private String status;
  private Date createdAt;
  private Date updatedAt;
}
