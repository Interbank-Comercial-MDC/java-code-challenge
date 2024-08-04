package com.interbank.challenge.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Document(collection = "table_role")
@Data
@NoArgsConstructor
public class RoleDocument {

    public RoleDocument(String role, LocalDate date){
        this.role=role;
        this.date=date;
    }
    @Id
    private String id;

    private String role;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

}
