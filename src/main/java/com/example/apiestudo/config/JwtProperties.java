package com.example.apiestudo.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "jwt")
@Validated
public class JwtProperties {

    @NotBlank
    private final String secret;

    @NotNull
    @Min(1)
    private final Long expiration;

    public JwtProperties(String secret, Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    //GETTERS
    public String getSecret() {
        return secret;
    }

    public Long getExpiration() {
        return expiration;
    }
}
