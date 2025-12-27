package com.example.apiestudo.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class UserUpdateDTO {

    @Size(max = 100, message = "Name must be shorter than 100 characters.")
    private String name;

    @Email(message = "Invalid email format.")
    private String email;

    @CPF(message = "Invalid CPF format.")
    private String CPF;

    @Pattern(regexp = "(^\\d{10,11}|\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$)", message = "Phone number format is invalid.")
    private String phoneNumber;

    // GETTERS
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

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
