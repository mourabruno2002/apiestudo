package com.example.apiestudo.dto.auth;

import java.time.Instant;
import java.util.Date;

public class LoginResponseDTO {

    private final String token;
    private final Date expiresAt;
    private final String type;

    public LoginResponseDTO(String token, Date expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.type = "Bearer";
    }

    // GETTERS
    public String getToken() {
        return token;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public String getType() {
        return type;
    }
}
