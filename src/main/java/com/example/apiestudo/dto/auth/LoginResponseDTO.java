package com.example.apiestudo.dto.auth;

import java.time.Instant;

public class LoginResponseDTO {

    private final String token;
    private final Instant expiresAt;
    private final String type;

    public LoginResponseDTO(String token, Instant expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.type = "Bearer";
    }

    // GETTERS
    public String getToken() {
        return token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public String getType() {
        return type;
    }
}
