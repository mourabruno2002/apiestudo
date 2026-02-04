package com.example.apiestudo.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(message = "E-mail is required.")
    @Email(message = "Invalid e-mail format.")
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;

    // GETTERS
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
