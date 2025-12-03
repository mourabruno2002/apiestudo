package com.example.apiestudo.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryRequestDTO {

    @NotNull(message = "Category is required.")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long.")
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

}
