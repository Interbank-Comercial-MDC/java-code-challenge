package com.prueba.antifraude.msantifraude.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class TransactionNew {
    @Id
    @JsonProperty("accountExternalId")
    private String accountExternalId;
    @JsonProperty("tranferTypeId")
    private int tranferTypeId;
    @JsonProperty("value")
    private Double value;

    private Date createdAt;
    @JsonIgnore
    private Date updateAt;


}