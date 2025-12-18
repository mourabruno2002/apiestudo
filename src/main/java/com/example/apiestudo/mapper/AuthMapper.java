package com.example.apiestudo.mapper;

import com.example.apiestudo.dto.auth.RegisterRequestDTO;
import com.example.apiestudo.dto.user.UserRequestDTO;
import com.example.apiestudo.utils.MapperUtils;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    private final MapperUtils mapperUtils;

    public AuthMapper(MapperUtils mapperUtils) {
        this.mapperUtils = mapperUtils;
    }

    public UserRequestDTO convertRegisterRequestToUserRequest(RegisterRequestDTO registerRequestDTO) {
        return mapperUtils.map(registerRequestDTO, UserRequestDTO.class);
    }
}
