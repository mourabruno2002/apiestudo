package com.example.apiestudo.mapper;

import com.example.apiestudo.dto.auth.RegisterRequestDTO;
import com.example.apiestudo.dto.user.UserRequestDTO;
import com.example.apiestudo.utils.MapperService;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    private final MapperService mapperService;

    public AuthMapper(MapperService mapperService) {
        this.mapperService = mapperService;
    }

    public UserRequestDTO convertRegisterRequestToUserRequest(RegisterRequestDTO registerRequestDTO) {
        return mapperService.map(registerRequestDTO, UserRequestDTO.class);
    }
}
