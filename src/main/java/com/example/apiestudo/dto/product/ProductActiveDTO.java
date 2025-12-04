package com.example.apiestudo.dto.product;

import jakarta.validation.constraints.NotNull;

public class ProductActiveDTO {

    @NotNull(message = "The field 'active' is required.")
    private Boolean active;

    // GETTER
    public Boolean getActive() {
        return active;
    }

    // SETTER
    public void setActive(boolean active) {
        this.active = active;
    }
}
