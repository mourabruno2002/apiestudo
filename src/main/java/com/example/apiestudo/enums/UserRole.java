package com.example.apiestudo.enums;

public enum UserRole {

    ADMIN,
    USER;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
