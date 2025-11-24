package com.example.apiestudo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordUpdateDTO {

    @NotBlank(message = "Current password is required.")
    @Size(min = 6, message = "Current password must contain at least 6 characters.")
    private String currentPassword;

    @NotBlank(message = "New password is required.")
    @Size(min = 6, message = "New password must contain at least 6 characters.")
    private String newPassword;

    // GETTERS
    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    // SETTERS
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
