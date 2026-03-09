package com.example.apiestudo.dto.auth;

import com.example.apiestudo.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public class UserRoleUpdateDTO {

    @NotNull(message = "Role is required.")
    private UserRole newRole;

    // GETTERS
    public UserRole getNewRole() {
        return newRole;
    }

    // SETTERS
    public void setNewRole(UserRole newRole) {
        this.newRole = newRole;
    }
}
