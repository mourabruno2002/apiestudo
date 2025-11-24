package com.example.apiestudo.mapper;

import com.example.apiestudo.dto.user.UserRequestDTO;
import com.example.apiestudo.dto.user.UserResponseDTO;
import com.example.apiestudo.dto.user.UserUpdateDTO;
import com.example.apiestudo.model.User;
import com.example.apiestudo.utils.MapperUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final MapperUtils mapperUtils;

    public UserMapper(MapperUtils mapperUtils) {
        this.mapperUtils = mapperUtils;
    }

    public User convertRequestToUser(UserRequestDTO userRequestDTO) {
        return mapperUtils.map(userRequestDTO, User.class);
    }

    public User convertUpdateToUser(UserUpdateDTO userUpdateDTO) {
        return mapperUtils.map(userUpdateDTO, User.class);
    }

    public UserResponseDTO convertUserToResponse(User user) {
        UserResponseDTO userResponseDTO = mapperUtils.map(user, UserResponseDTO.class);
        userResponseDTO.setEmail(maskEmail(user.getUsername()));
        userResponseDTO.setSystem("Clients API v1");

        return userResponseDTO;
    }

    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;

        String[] parts = email.split("@");
        String name = parts[0];
        String domain = parts[1];
        int visible = Math.min(2, name.length());

        return name.substring(0, visible) + "*******@" + domain;
    }
}
