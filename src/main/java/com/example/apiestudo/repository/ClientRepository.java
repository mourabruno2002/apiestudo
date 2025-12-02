package com.example.apiestudo.repository;

import com.example.apiestudo.dto.client.ClientResponseDTO;
import com.example.apiestudo.model.Client;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByCPF(String CPF);
}
