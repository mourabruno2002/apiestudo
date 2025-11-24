package com.example.apiestudo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters long.")
    private String name;

    @Email(message = "Invalid email format.")
    @Size(min = 1, message = "Invalid email format.")
    private String email;

    // GETTERS
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
