package com.example.apiestudo.mapper;

import com.example.apiestudo.dto.auth.RegisterRequestDTO;
import com.example.apiestudo.dto.user.UserRequestDTO;
import com.example.apiestudo.GenericConverter;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    private final GenericConverter genericConverter;

    public AuthMapper(GenericConverter genericConverter) {
        this.genericConverter = genericConverter;
    }

    public UserRequestDTO convertRegisterRequestToUserRequest(RegisterRequestDTO registerRequestDTO) {
        return genericConverter.map(registerRequestDTO, UserRequestDTO.class);
    }
}
