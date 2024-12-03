package com.prueba.crea.msgestionatransaction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "transaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private String accountExternalId;
    @NotNull(message = "Ingrese tipo de transferencia")
    private int tranferTypeId;
    @NotNull(message = "Ingrese monto de transferencia")
    private Double value;
    @JsonIgnore
    private String status;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private Date updateAt;

}
