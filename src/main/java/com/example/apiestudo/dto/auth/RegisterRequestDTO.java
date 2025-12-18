package com.example.apiestudo.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {

    @NotBlank(message = "Name is required.")
    @Size(min = 2, max = 100, message = "Name must contain between 2 and 100 characters.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password must be longer than 6 characters.")
    private String password;

    //GETTER

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
