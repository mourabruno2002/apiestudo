package com.example.apiestudo.mapper;

import com.example.apiestudo.dto.client.ClientRequestDTO;
import com.example.apiestudo.dto.client.ClientResponseDTO;
import com.example.apiestudo.model.Client;
import com.example.apiestudo.utils.MapperUtils;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    private final MapperUtils mapperUtils;

    public ClientMapper(MapperUtils mapperUtils) {
        this.mapperUtils = mapperUtils;
    }

    public Client convertRequestToClient(ClientRequestDTO clientRequestDTO) {
        return mapperUtils.map(clientRequestDTO, Client.class);
    }

    public ClientResponseDTO convertClientToResponse(Client client) {

        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                maskEmail(client.getEmail()),
                maskPhone(client.getPhoneNumber()),
                client.getSystem(),
                client.isActive(),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }

    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;

        String[] parts = email.split("@");
        String name = parts[0];
        String domain = parts[1];
        int visibles = Math.min(2, name.length());

        return name.substring(0, visibles) + "*******@" + domain;
    }

    private String maskPhone(String phone) {
        if (phone.length() < 8) return phone;

        return "********" + phone.substring(phone.length() -2);
    }

}
