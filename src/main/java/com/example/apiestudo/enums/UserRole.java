package com.example.apiestudo.enums;

public enum UserRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String authorities;

    UserRole(String authorities) {
        this.authorities = authorities;
    }

    public String getAuthorities() {
        return authorities;
    }
}
