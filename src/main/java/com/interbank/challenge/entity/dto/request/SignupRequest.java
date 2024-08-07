package com.interbank.challenge.entity.dto.request;

import lombok.Data;

@Data
public class SignupRequest {

    private String user;
    private String password;
    private String email;
    private boolean lock;
    private boolean disable;
    private String role;

}
