package com.interbank.challenge.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {

    private Integer codeResponse;
    private String messageResponse;
}
