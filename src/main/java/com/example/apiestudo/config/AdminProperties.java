package com.example.apiestudo.config;

import com.example.apiestudo.enums.UserRole;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "admin")
public class AdminProperties {

    private String username;
    private String password;
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
}
