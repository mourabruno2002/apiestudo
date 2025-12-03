package com.example.apiestudo.repository;

import com.example.apiestudo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByCpf(String CPF);
}
