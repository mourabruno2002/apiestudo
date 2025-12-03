package com.example.apiestudo.dto.client;

import jakarta.persistence.Version;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class ClientResponseDTO {
    private final Long id;

    private final String name;

    private final String email;

    private final String phoneNumber;

    private final String system;

    private boolean active;

    @CreationTimestamp
    private final LocalDateTime createdAt;

    @UpdateTimestamp
    private final LocalDateTime updatedAt;

    @Version
    private Long version;


    public ClientResponseDTO(Long id, String name, String email, String phoneNumber, String system, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.system = system;
        this.active = active;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSystem() {
        return system;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getVersion() {
        return version;
    }
}

