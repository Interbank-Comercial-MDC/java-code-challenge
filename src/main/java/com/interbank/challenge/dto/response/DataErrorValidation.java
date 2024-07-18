package com.interbank.challenge.dto.response;

import lombok.Data;
import org.springframework.validation.FieldError;
@Data
public class DataErrorValidation {
    private String campo;
    private String error;

    public DataErrorValidation(FieldError error) {
        this.campo = error.getField();
        this.error = error.getDefaultMessage();
    }


}
