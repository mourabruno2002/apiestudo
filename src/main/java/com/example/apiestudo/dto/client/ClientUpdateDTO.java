package com.example.apiestudo.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class ClientUpdateDTO {

    @Size(max = 100, message = "Name must be shorter than 100 characters.")
    String name;

    @CPF(message = "Invalid CPF format.")
    private String CPF;

    @Email(message = "Invalid email format.")
    private String email;

    @Pattern(regexp = "(^\\d{10,11}|\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$)", message = "Phone number format is invalid.")
    private String phoneNumber;

    //GETTERS

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

    //SETTERS

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
