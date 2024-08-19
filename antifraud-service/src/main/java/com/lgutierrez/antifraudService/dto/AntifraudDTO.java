package com.lgutierrez.antifraudService.dto;

import java.util.UUID;

public record AntifraudDTO(
        UUID accountExternalIdDebit,
        UUID accountExternalIdCredit,
        String transferTypeId,
        Double value
) {
    public AntifraudDTO {

    }
}
