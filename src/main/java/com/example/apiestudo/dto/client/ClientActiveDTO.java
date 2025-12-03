package com.example.apiestudo.dto.client;

import jakarta.validation.constraints.NotNull;

public class ClientActiveDTO {

    @NotNull(message = "Active is required.")
    private Boolean active;

    // GETTERS
    public Boolean getActive() {
        return active;
    }

    // SETTER
    public void setActive(boolean active) {
        this.active = active;
    }
}
