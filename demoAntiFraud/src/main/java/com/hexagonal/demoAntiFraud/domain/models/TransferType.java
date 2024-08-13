package com.hexagonal.demoAntiFraud.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.hexagonal.demoAntiFraud.infrastructure.util.Constants.*;

@Getter
@AllArgsConstructor
public enum TransferType {
    OWN_ACCOUNT(1, DESC_OWN_ACCOUNT),
    OTHER_ACCOUNT_IBK(2, DESC_OTHER_ACCOUNT_IBK),
    THIRD_PARTY_ACCOUNT(3, DESC_THIRD_PARTY_ACCOUNT);

    private final int id;
    private final String description;

    public static String getValueByID(int id) {
        for (TransferType type : TransferType.values()) {
            if (type.getId() == id) {
                return type.getDescription();
            }
        }
        return null;
    }
}