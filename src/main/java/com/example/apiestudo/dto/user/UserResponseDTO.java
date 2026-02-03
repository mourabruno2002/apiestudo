package com.example.apiestudo.dto.user;

import com.example.apiestudo.enums.UserRole;

import java.time.Instant;

public class UserResponseDTO {
    private final Long id;

    private final String name;

    private final String email;

    private final String cpf;

    private final String phoneNumber;

    private final UserRole role;

    private final String system;

    private final Instant createdAt;

    private final Instant updatedAt;

    public UserResponseDTO(Long id, String email, String name, String cpf, String phoneNumber, UserRole role, String system, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.system = system;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // GETTERS
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public String getSystem() {
        return system;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
