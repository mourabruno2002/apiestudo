package com.example.apiestudo.service;

import com.example.apiestudo.exception.ClientNotFoundException;
import com.example.apiestudo.mapper.ClientMapper;
import com.example.apiestudo.repository.ClientRepository;
import com.example.apiestudo.dto.client.ClientResponseDTO;
import com.example.apiestudo.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public ClientResponseDTO createClient(Client client) {
        Client newClient = clientRepository.save(client);

        return clientMapper.convertClientToResponse(newClient);
    }

    public Page<ClientResponseDTO> findAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::convertClientToResponse);
    }

    public Page<ClientResponseDTO> findClientsByName(String name, Pageable pageable) {
        return clientRepository.findClientsByName(name, pageable);
    }

    public ClientResponseDTO findClientById(Long id) {
        Client foundClient = getClientById(id);

        return clientMapper.convertClientToResponse(foundClient);
    }

    public ClientResponseDTO deleteClient(Long id) {
        Client deletedClient = getClientById(id);
        clientRepository.deleteById(id);

        return clientMapper.convertClientToResponse(deletedClient);
    }

    public ClientResponseDTO updateClient(Long id, Client newData) {
        Client existing = getClientById(id);

        if (existing != null) {
            if (newData.getName() != null) {
                existing.setName(newData.getName());
            }
            if (newData.getCPF() != null) {
                existing.setCPF(newData.getCPF());
            }
            if (newData.getEmail() != null) {
                existing.setEmail(newData.getEmail());
            }
            if (newData.getPhoneNumber() != null) {
                existing.setPhoneNumber(newData.getPhoneNumber());
            }

            clientRepository.save(existing);
        }

        return clientMapper.convertClientToResponse(existing);
    }

    // INTERNAL METHODS
    protected Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(String.format("Client with ID %d not found.", id)));
    }
}
