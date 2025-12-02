package com.example.apiestudo.controller;

import com.example.apiestudo.dto.client.ClientRequestDTO;
import com.example.apiestudo.dto.client.ClientResponseDTO;
import com.example.apiestudo.mapper.ClientMapper;
import com.example.apiestudo.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }


    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(clientRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> findAll(Pageable pageable) {

        return ResponseEntity.ok(clientService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok(clientService.findById(id));
    }



}
