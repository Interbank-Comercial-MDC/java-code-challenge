package com.interbank.challenge.entity.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SigninRequest {

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
