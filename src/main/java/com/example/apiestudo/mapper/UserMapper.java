package com.example.apiestudo.mapper;

import com.example.apiestudo.dto.user.UserRequestDTO;
import com.example.apiestudo.dto.user.UserResponseDTO;
import com.example.apiestudo.dto.user.UserUpdateDTO;
import com.example.apiestudo.model.User;
import com.example.apiestudo.utils.EmailUtils;
import com.example.apiestudo.utils.MapperUtils;
import org.springframework.stereotype.Component;

import static com.example.apiestudo.utils.EmailUtils.maskEmail;

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
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                EmailUtils.maskEmail(user.getUsername()),
                user.getRole(),
                user.getSystem()
        );
    }
}
