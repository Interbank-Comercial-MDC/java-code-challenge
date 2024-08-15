package org.antifraude.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransferType {
    OWN_ACCOUNT(1, "cuenta propia"),
    OTHER_ACCOUNT_IBK(2, "otras cuentas IBK"),
    THIRD_PARTY_ACCOUNT(3, "cuenta de terceros");

    private final int id;
    private final String description;

    public static String getValueByID(int id) {
        for (TransferType type : TransferType.values()) {
            if (type.getId() == id) {
                return type.getDescription();
            }
        }
        return "";
    }

}
