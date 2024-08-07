package com.interbank.challenge.entity.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TransactionRequest {

    @Size(min = 36, max = 36)
    private String id;

    @NotNull
    @Size(min = 36, max = 36)
    private String accountDebit;

    @NotNull
    @Size(min = 36, max = 36)
    private String accountCredit;

    @NotNull
    @Positive
    private Integer type;

    @NotNull
    @Positive
    private Integer values;

    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

}
