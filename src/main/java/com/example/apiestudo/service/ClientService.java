package com.example.apiestudo.service;

import com.example.apiestudo.dto.client.ClientActiveDTO;
import com.example.apiestudo.dto.client.ClientRequestDTO;
import com.example.apiestudo.dto.client.ClientUpdateDTO;
import com.example.apiestudo.exception.FieldRequiredException;
import com.example.apiestudo.exception.client.ClientAlreadyExistsException;
import com.example.apiestudo.exception.client.ClientNotFoundException;
import com.example.apiestudo.mapper.ClientMapper;
import com.example.apiestudo.repository.ClientRepository;
import com.example.apiestudo.dto.client.ClientResponseDTO;
import com.example.apiestudo.model.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Transactional
    public ClientResponseDTO create(ClientRequestDTO clientRequestDTO) {
        if (clientRepository.existsByCPF(clientRequestDTO.getCPF())) {
            throw new ClientAlreadyExistsException("A client with this CPF already exists.");
        }

        if (clientRepository.existsByEmail(clientRequestDTO.getEmail())) {
            throw new ClientAlreadyExistsException("A client with this email already exists.");
        }

        Client client = clientMapper.convertRequestToClient(clientRequestDTO);

        client.setActive(true);
        client.setSystem("Client V2");

        return clientMapper.convertClientToResponse(clientRepository.save(client));
    }

    public Page<ClientResponseDTO> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::convertClientToResponse);
    }

    public ClientResponseDTO findById(Long id) {

        return clientMapper.convertClientToResponse(clientRepository.findById(id).orElseThrow(
                () -> new ClientNotFoundException(("Client not found.")))
        );
    }


    @Transactional
    public void deleteById(Long id) {
        getById(id);

        clientRepository.deleteById(id);
    }

    @Transactional
    public ClientResponseDTO update(Long id, ClientUpdateDTO newData) {
        Client client = getById(id);

        if (newData.getName() != null) {
            client.setName(newData.getName());
        }
        if (newData.getCPF() != null) {
            if (!Objects.equals(newData.getCPF(), client.getCpf())) {

                if (clientRepository.existsByCPF(newData.getCPF())) {
                    throw new ClientAlreadyExistsException("A client with this CPF already exists.");
                }

                client.setCpf(newData.getCPF());
            }
        }

        if (newData.getEmail() != null) {
            if (!Objects.equals(newData.getEmail(), client.getEmail())) {
                if (clientRepository.existsByEmail(newData.getEmail())) {
                    throw new ClientAlreadyExistsException("A client with this email already exists.");
                }
            }

            client.setEmail(newData.getEmail());
        }

        if (newData.getPhoneNumber() != null) {
            client.setPhoneNumber(newData.getPhoneNumber());
        }

        return clientMapper.convertClientToResponse(clientRepository.save(client));
    }

    @Transactional
    public ClientResponseDTO updateActive(Long id, ClientActiveDTO clientActiveDTO) {
        Client client = getById(id);

        if (clientActiveDTO == null) {
            throw new FieldRequiredException("The field 'active' is required.");
        }

        client.setActive(clientActiveDTO.getActive());

        return clientMapper.convertClientToResponse(client);
    }

    // INTERNAL METHODS
    protected Client getById(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new ClientNotFoundException(String.format("Client with ID %d not found.", id)));
    }
}
