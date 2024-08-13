package com.hexagonal.demoAntiFraud.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.hexagonal.demoAntiFraud.infrastructure.util.Constants.*;

@AllArgsConstructor
@Getter
public enum TransferStatus {

    PENDING(1, STATUS_PENDING),
    APPROVED(2, STATUS_APPROVED),
    REFUSED(3, STATUS_REFUSED);

    final int id;
    final String name;

    public static String getValueByID(int id) {
        for (TransferStatus status : TransferStatus.values()) {
            if (status.getId() == id) {
                return status.getName();
            }
        }
        return "";
    }

}