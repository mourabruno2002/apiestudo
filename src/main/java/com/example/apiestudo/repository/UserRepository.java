package com.example.apiestudo.repository;

import com.example.apiestudo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String email);

    boolean existsByUsername(String email);

    boolean existsByUsernameAndIdNot(String username, Long id);
}
