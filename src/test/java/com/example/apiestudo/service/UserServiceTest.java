package com.example.apiestudo.service;

import com.example.apiestudo.dto.user.UserRequestDTO;
import com.example.apiestudo.exception.domain.user.UserAlreadyExistsException;
import com.example.apiestudo.exception.domain.user.WeakPasswordException;
import com.example.apiestudo.mapper.UserMapper;
import com.example.apiestudo.model.User;
import com.example.apiestudo.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    
    @Nested
    class CreateUser {
        @Test
        void shouldThrowExceptionWhenUsernameAlreadyExists() {
            UserRequestDTO userRequestDTO = new UserRequestDTO();
            userRequestDTO.setUsername("test@email.com");
            userRequestDTO.setPassword("Password@123");

            User user = new User();
            user.setUsername("test@email.com");
            user.setPassword("Password@123");

            when(userMapper.convertRequestToUser(userRequestDTO)).thenReturn(user);
            when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

            assertThrows(UserAlreadyExistsException.class, () -> {
                userService.create(userRequestDTO);
            });

            verify(userRepository, never()).save(any());
            verify(passwordEncoder, never()).encode(any());
        }

        @Test
        void shouldThrowExceptionWhenCpfAlreadyExists() {
            UserRequestDTO userRequestDTO = new UserRequestDTO();
            userRequestDTO.setUsername("test@email.com");
            userRequestDTO.setPassword("Password@123");
            userRequestDTO.setCpf("13197026942");

            User user = new User();
            user.setUsername("test@email.com");
            user.setPassword("Password@123");
            user.setCpf("13197026942");

            when(userMapper.convertRequestToUser(userRequestDTO)).thenReturn(user);
            when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
            when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));

            assertThrows(UserAlreadyExistsException.class, () -> {
                userService.create(userRequestDTO);
            });

            verify(userRepository, never()).save(any());
            verify(passwordEncoder, never()).encode(any());
        }

        @Test
        void shouldThrowExceptionWhenPasswordIsWeak() {
            UserRequestDTO userRequestDTO = new UserRequestDTO();
            userRequestDTO.setUsername("test@email.com");
            userRequestDTO.setPassword("123");
            userRequestDTO.setCpf("13197026942");

            User user = new User();
            user.setUsername("test@email.com");
            user.setPassword("123");
            user.setCpf("13197026942");

            when(userMapper.convertRequestToUser(userRequestDTO)).thenReturn(user);
            when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
            when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.empty());

            assertThrows(WeakPasswordException.class, () -> {
               userService.validatePassword(user.getPassword(), user.getUsername());
            });

            verify(passwordEncoder, never()).encode(any());
            verify(userRepository, never()).save(any());
        }

    }
}
