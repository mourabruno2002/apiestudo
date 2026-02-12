package com.example.apiestudo.dto.auth;

import com.example.apiestudo.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public class UserRoleUpdateDTO {

    @NotNull(message = "Role is required.")
    private UserRole role;

    // GETTERS
    public UserRole getRole() {
        return role;
    }

}
