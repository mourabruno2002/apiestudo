package com.example.apiestudo.dto.category;

import java.time.Instant;
import java.time.LocalDateTime;

public class CategoryResponseDTO {
    private final Long id;

    private final String name;

    private final String description;

    private final boolean active;

    private final Instant createdAt;

    private final Instant updatedAt;

    public CategoryResponseDTO(Long id, String name, String description, boolean active, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
