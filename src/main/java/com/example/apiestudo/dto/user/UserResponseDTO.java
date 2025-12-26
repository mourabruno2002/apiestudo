package com.example.apiestudo.dto.user;

import com.example.apiestudo.enums.UserRole;

public class UserResponseDTO {
    private final Long id;

    private final String name;

    private final String email;

    private final String CPF;

    private final String phoneNumber;

    private final UserRole role;

    private final String system;

    public UserResponseDTO(Long id, String email, String name, String CPF, String phoneNumber, UserRole role, String system) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.CPF = CPF;
        this.phoneNumber = phoneNumber;
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

    public String getCPF() {
        return CPF;
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

}
