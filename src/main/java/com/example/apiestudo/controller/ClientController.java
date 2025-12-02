package com.example.apiestudo.controller;

import com.example.apiestudo.dto.client.ClientRequestDTO;
import com.example.apiestudo.dto.client.ClientResponseDTO;
import com.example.apiestudo.mapper.ClientMapper;
import com.example.apiestudo.service.ClientService;
import com.example.apiestudo.utils.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<MessageResponse<ClientResponseDTO>> createClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO newClient = clientService.createClient(clientMapper.convertRequestToClient(clientRequestDTO));
        MessageResponse<ClientResponseDTO> response = new MessageResponse<>("Client created successfully.", newClient);

        return ResponseEntity.status(200).body(response);
    }

}
