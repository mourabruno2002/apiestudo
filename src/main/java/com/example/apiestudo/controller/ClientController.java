package com.example.apiestudo.controller;

import com.example.apiestudo.dto.client.ClientActiveDTO;
import com.example.apiestudo.dto.client.ClientRequestDTO;
import com.example.apiestudo.dto.client.ClientResponseDTO;
import com.example.apiestudo.dto.client.ClientUpdateDTO;
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

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
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

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClientUpdateDTO clientUpdateDTO) {

        return ResponseEntity.ok(clientService.update(id, clientUpdateDTO));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<ClientResponseDTO> updateActive(@PathVariable Long id, @Valid @RequestBody ClientActiveDTO clientActiveDTO) {

        return ResponseEntity.ok(clientService.updateActive(id, clientActiveDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
