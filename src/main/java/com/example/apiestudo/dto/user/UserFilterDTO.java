package com.example.apiestudo.dto.user;

import com.example.apiestudo.enums.UserRole;

public class UserFilterDTO {

    private String name;
    private UserRole role;
    private Boolean active;

    //GETTERS
    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public Boolean getActive() {
        return active;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
