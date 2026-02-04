package com.example.apiestudo.config;

import com.example.apiestudo.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "admin")
@Validated
public class AdminProperties {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private UserRole role;

    //GETTERS
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    //SETTERS

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
