package com.example.apiestudo.dto.user;

public class UserResponseDTO {
    private final Long id;

    private final String name;

    private final String email;

    private final String role;

    private final String system;

    public UserResponseDTO(Long id, String email, String name, String role, String system) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.system = system;
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

    public String getRole() {
        return role;
    }

    public String getSystem() {
        return system;
    }

}
