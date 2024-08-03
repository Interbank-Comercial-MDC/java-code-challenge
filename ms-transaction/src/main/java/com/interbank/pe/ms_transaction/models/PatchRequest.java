package com.interbank.pe.ms_transaction.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatchRequest {
    private Integer statusCode;
}
