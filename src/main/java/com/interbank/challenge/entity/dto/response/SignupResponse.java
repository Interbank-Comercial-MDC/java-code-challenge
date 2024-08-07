package com.interbank.challenge.entity.dto.response;

import lombok.Data;

@Data
public class SignupResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String id;
    private String username;
    private String email;
    private Boolean locked = true;
    private Boolean disable = true;
    private String role;
}
