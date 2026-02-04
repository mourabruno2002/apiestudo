package com.example.apiestudo.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@ConfigurationProperties(prefix = "jwt")
@Validated
public class JwtProperties {

    @NotBlank
    private String secret;

    @NotNull
    private Long expiration;

    //GETTERS
    public String getSecret() {
        return secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    //SETTER
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}
