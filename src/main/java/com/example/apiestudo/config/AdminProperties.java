package com.example.apiestudo.config;

import com.example.apiestudo.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "admin")
@Validated
public class AdminProperties {

    @NotBlank
    private final String username;

    @NotBlank
    @Size(min = 8)
    private final String password;

    @NotNull
    private final UserRole role;

    public AdminProperties(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //GETTERS
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
}
