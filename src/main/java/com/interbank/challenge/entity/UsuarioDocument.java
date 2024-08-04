package com.interbank.challenge.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "table_user")
@Data
@NoArgsConstructor
public class UsuarioDocument {

    public UsuarioDocument(String username, String password, String email, Boolean locked, Boolean disable) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.locked = locked;
        this.disable = disable;
    }

    public UsuarioDocument(String username, String password, String email, Boolean locked, Boolean disable, RoleDocument roleDocument) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.locked = locked;
        this.disable = disable;
        this.roleDocument = roleDocument;
    }

    @Id
    private String id;

    private String username;

    private String password;

    private String email;

    private Boolean locked;

    private Boolean disable;

    private RoleDocument roleDocument;


}



