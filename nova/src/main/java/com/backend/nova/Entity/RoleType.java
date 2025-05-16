package com.backend.nova.Entity;

import lombok.Getter;

@Getter
public enum RoleType {
    ADMIN(1),
    ENCARGADO(2),
    CLIENTE(3);

    private int value;

    private RoleType(int value) {
        this.value = value;
    }
}
