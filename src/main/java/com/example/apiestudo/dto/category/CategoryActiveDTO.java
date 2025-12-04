package com.example.apiestudo.dto.category;

import jakarta.validation.constraints.NotNull;

public class CategoryActiveDTO {

    @NotNull(message = "The field 'active' is required.")
    private Boolean active;

    // GETTER
    public Boolean getActive() {
        return active;
    }

    // SETTER
    public void setActive(Boolean active) {
        this.active = active;
    }
}
