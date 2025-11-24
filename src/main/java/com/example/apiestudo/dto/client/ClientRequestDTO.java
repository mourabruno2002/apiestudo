package com.example.apiestudo.dto.client;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class ClientRequestDTO {

    @NotBlank(message = "Name is required.")
    @Size(min = 2, max = 100, message = "Name must contain between 2 and 100 characters.")
    String name;

    @Column(unique = true)
    @NotBlank(message = "CPF is required.")
    @CPF
    private String CPF;

    @Column(unique = true)
    @Email
    @NotBlank(message = "Email is required.")
    private String email;

    @Pattern(regexp = "(^\\d{10,11}|\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$)", message = "Phone number format is invalid.")
    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public String getCPF() {
        return CPF;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
