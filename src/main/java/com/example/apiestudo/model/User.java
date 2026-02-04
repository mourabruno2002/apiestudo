package com.example.apiestudo.model;

import com.example.apiestudo.enums.UserRole;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String cpf;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String system;

    private boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getAuthorities()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //GETTERS
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getCpf() {
        return cpf;
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

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
