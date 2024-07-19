package com.ibk.antifraude.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusTransfer {

    PENDING(1, "pendiente"),
    APPROVED(2, "aprobado"),
    REFUSED(3, "rechazado");

    final int id;
    final String name;

    public static String getValueByID(int id) {
        for (StatusTransfer status : StatusTransfer.values()) {
            if (status.getId() == id) {
                return status.getName();
            }
        }
        return "";
    }

}
