package org.antifraude.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusTransfer {

    PENDING(1, "pendiente"),
    APPROVED(2, "aprobado"),
    REFUSED(3, "rechazado");

    private final int id;
    private final String name;

    public static String getValueByID(int id) {
        for (StatusTransfer status : StatusTransfer.values()) {
            if (status.getId() == id) {
                return status.getName();
            }
        }
        return "";
    }

}
