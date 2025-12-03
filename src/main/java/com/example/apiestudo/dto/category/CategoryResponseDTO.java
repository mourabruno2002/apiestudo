package com.example.apiestudo.dto.category;

import com.example.apiestudo.repository.CategoryRepository;

import java.time.LocalDateTime;

public class CategoryResponseDTO {
    private final Long id;

    private final String name;

    private final String description;

    private final boolean active;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public CategoryResponseDTO(Long id, String name, String description, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
