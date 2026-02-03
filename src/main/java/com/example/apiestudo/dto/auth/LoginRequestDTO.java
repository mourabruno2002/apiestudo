package com.example.apiestudo.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {

    @NotBlank(message = "E-mail is required.")
    @Email(message = "Invalid e-mail format.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be longer than 8 characters.")
    private String password;

    // GETTERS
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
