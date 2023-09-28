package com.user.userservice.enums;

public enum RoleName {
    ADMIN("ADMIN"), VENDOR("VENDOR"), USER("USER");

    private String value;

    RoleName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
