package com.example.apiestudo.dto.client;

public class ClientResponseDTO {
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String system;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSystem(String system) {
        this.system = system;
    }
}
