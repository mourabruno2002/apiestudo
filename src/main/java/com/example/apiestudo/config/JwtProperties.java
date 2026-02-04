package com.example.apiestudo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;

    private Long expiration;

    // GETTERS
    public String getSecret() {
        return secret;
    }

    public Long getExpiration() {
        return expiration;
    }
}
