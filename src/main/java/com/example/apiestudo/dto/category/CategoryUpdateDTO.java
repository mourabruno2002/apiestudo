package com.example.apiestudo.dto.category;

import jakarta.validation.constraints.Size;

public class CategoryUpdateDTO {

    @Size(max = 50, message = "Name must be shorter than 50 characters.")
    private String name;

    @Size(max = 255, message = "Description must be shorter than 255 characters.")
    private String description;

    // GETTERS
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
