package com.example.apiestudo.mapper;

import com.example.apiestudo.dto.user.UserRequestDTO;
import com.example.apiestudo.dto.user.UserResponseDTO;
import com.example.apiestudo.model.User;
import com.example.apiestudo.utils.EmailUtils;
import com.example.apiestudo.GenericConverter;
import com.example.apiestudo.utils.PhoneNumberUtils;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class UserMapper {

    private final GenericConverter genericConverter;

    public UserMapper(GenericConverter genericConverter) {
        this.genericConverter = genericConverter;
    }

    public User convertRequestToUser(UserRequestDTO userRequestDTO) {
        return genericConverter.map(userRequestDTO, User.class);
    }

    public UserResponseDTO convertUserToResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                EmailUtils.maskEmail(user.getUsername()),
                user.getCpf(),
                PhoneNumberUtils.maskPhoneNumber(user.getPhoneNumber()),
                user.getRole(),
                user.getSystem(),
                user.getCreatedAt().atZone(ZoneOffset.UTC).toInstant(),
                user.getUpdatedAt().atZone(ZoneOffset.UTC).toInstant()
        );
    }

}
