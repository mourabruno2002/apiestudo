package com.example.apiestudo.service;

import com.example.apiestudo.dto.auth.UserRoleUpdateDTO;
import com.example.apiestudo.dto.user.PasswordUpdateDTO;
import com.example.apiestudo.dto.user.UserRequestDTO;
import com.example.apiestudo.dto.user.UserResponseDTO;
import com.example.apiestudo.dto.user.UserUpdateDTO;
import com.example.apiestudo.enums.UserRole;
import com.example.apiestudo.exception.domain.user.InvalidCurrentPasswordException;
import com.example.apiestudo.exception.domain.user.UserAlreadyExistsException;
import com.example.apiestudo.exception.domain.user.UserNotFoundException;
import com.example.apiestudo.exception.domain.user.WeakPasswordException;
import com.example.apiestudo.mapper.UserMapper;
import com.example.apiestudo.model.User;
import com.example.apiestudo.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    class Create {
        @Test
        void shouldThrowUserAlreadyExistsExceptionWhenUsernameAlreadyExists() {
            UserRequestDTO userRequestDTO = new UserRequestDTO();
            userRequestDTO.setUsername("test@email.com");
            userRequestDTO.setPassword("Password@123");

            User user = new User();
            user.setUsername("test@email.com");
            user.setPassword("Password@123");

            when(userMapper.convertRequestToUser(any())).thenReturn(user);
            when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

            assertThrows(UserAlreadyExistsException.class, () -> {
                userService.create(userRequestDTO);
            });

            verify(userRepository, never()).save(any());
            verify(passwordEncoder, never()).encode(any());
        }

        @Test
        void shouldThrowUserAlreadyExistsExceptionWhenCpfAlreadyExists() {
            UserRequestDTO userRequestDTO = new UserRequestDTO();
            userRequestDTO.setUsername("test@email.com");
            userRequestDTO.setPassword("Password@123");
            userRequestDTO.setCpf("13197026942");

            User user = new User();
            user.setUsername("test@email.com");
            user.setPassword("Password@123");
            user.setCpf("13197026942");

            when(userMapper.convertRequestToUser(any())).thenReturn(user);
            when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
            when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));

            assertThrows(UserAlreadyExistsException.class, () -> {
                userService.create(userRequestDTO);
            });

            verify(userRepository, never()).save(any());
            verify(passwordEncoder, never()).encode(any());
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "test@email.com",
                "Pass@12",
                "PASSWORD@123",
                "password@123",
                "Password@",
                "Password123"
        })
        void shouldThrowWeakPasswordExceptionWhenPasswordIsWeak(String invalidPassword) {
            UserRequestDTO userRequestDTO = new UserRequestDTO();
            userRequestDTO.setUsername("test@email.com");
            userRequestDTO.setPassword(invalidPassword);
            userRequestDTO.setCpf("13197026942");

            User user = new User();
            user.setUsername("test@email.com");
            user.setPassword(invalidPassword);
            user.setCpf("13197026942");

            when(userMapper.convertRequestToUser(any())).thenReturn(user);
            when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
            when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.empty());

            assertThrows(WeakPasswordException.class, () -> {
               userService.create(userRequestDTO);
            });

            verify(passwordEncoder, never()).encode(any());
            verify(userRepository, never()).save(any());
        }

        @Test
        void shouldCreateUserSuccessfully() {

            UserRequestDTO userRequestDTO = new UserRequestDTO();
            userRequestDTO.setUsername("test@email.com");
            userRequestDTO.setPassword("Password@123");
            userRequestDTO.setCpf("12345678910");

            User user = new User();
            user.setUsername("test@email.com");
            user.setPassword("Password@123");
            user.setCpf("12345678910");

            when(userMapper.convertRequestToUser(any())).thenReturn(user);
            when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
            when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.empty());
            when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

            userService.create(userRequestDTO);

            ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

            verify(userRepository).save(userArgumentCaptor.capture());

            User userSaved = userArgumentCaptor.getValue();

            assertEquals("encodedPassword", userSaved.getPassword());
            assertEquals(UserRole.USER, userSaved.getRole());
            assertTrue(userSaved.isActive());
            assertEquals("SYSTEM-V1", userSaved.getSystem());
        }

    }

    @Nested
    class FindByIdUser {

        @Test
        void shouldThrowUserNotFoundExceptionWhenIdDoesNotExists() {
            Long id = 10L;

            when(userRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> {
                userService.findById(id);
            });

            verify(userMapper, never()).convertUserToResponse(any());
        }

        @Test
        void shouldReturnUserWhenIdExists() {
            Long id = 10L;

            User user = new User();
            user.setUsername("test@email.com");

            UserResponseDTO userResponseDTO = new UserResponseDTO(
                    10L,
                    "Test",
                    "test@email.com",
                    "12345678910",
                    "41999999999",
                    UserRole.USER,
                    "SYSTEM",
                    Instant.now(),
                    Instant.now()
            );

            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            when(userMapper.convertUserToResponse(user)).thenReturn(userResponseDTO);

            UserResponseDTO result = userService.findById(id);

            assertSame(userResponseDTO, result);

            verify(userRepository).findById(id);
        }
    }

    @Nested
    class FindAllUsers {

        @Test
        void shouldReturnUserPageWithOneUser() {
            User user = new User();
            UserResponseDTO userResponseDTO = new UserResponseDTO(
                    10L,
                    "Test",
                    "test@email.com",
                    "12345678910",
                    "41999999999",
                    UserRole.USER,
                    "SYSTEM-V1",
                    Instant.now(),
                    Instant.now()
            );
            Page<User> userPage = new PageImpl<>(List.of(user));
            Pageable pageable = PageRequest.of(0, 10);

            when(userRepository.findAll(pageable)).thenReturn(userPage);
            when(userMapper.convertUserToResponse(user)).thenReturn(userResponseDTO);

            Page<UserResponseDTO> result = userService.findAll(pageable);

            assertSame(userResponseDTO, result.getContent().get(0));
            assertEquals(1, result.getTotalElements());

            verify(userRepository).findAll(pageable);
        }

        @Test
        void shouldReturnEmptyUserPage() {
            Pageable pageable = PageRequest.of(0, 10);

            when(userRepository.findAll(pageable)).thenReturn(Page.empty());

            Page<UserResponseDTO> result = userService.findAll(pageable);

            assertEquals(0, result.getTotalElements());

            verify(userRepository).findAll(pageable);
        }
    }

    @Nested
    class UpdateUser {
        @Test
        void shouldThrowsUserNotFoundExceptionWhenIdDoesNotExists() {
            Long id = 10L;
            UserUpdateDTO userUpdateDTO = new UserUpdateDTO();

            when(userRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> {
                userService.update(id, userUpdateDTO);
            });

            verify(userRepository, never()).existsByUsernameAndIdNot(any(), any());
            verify(userRepository, never()).existsByCpfAndIdNot(any(), any());
            verify(userRepository, never()).save(any());
            verify(userRepository).findById(id);
            verify(userMapper, never()).convertUserToResponse(any());
        }

        @Test
        void shouldThrowsUserAlreadyExistsWhenUsernameAlreadyExists() {

            Long id = 10L;
            UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
            userUpdateDTO.setEmail("valid@email.com");

            User user = new User();

            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            when(userRepository.existsByUsernameAndIdNot(userUpdateDTO.getEmail(), user.getId())).thenReturn(true);

            assertThrows(UserAlreadyExistsException.class, () -> {
               userService.update(id, userUpdateDTO);
            });

            verify(userRepository, never()).existsByCpfAndIdNot(any(), any());
            verify(userRepository, never()).save(any());
            verify(userMapper, never()).convertUserToResponse(any());
            verify(userRepository).findById(id);
        }

        @Test
        void shouldThrowsUserAlreadyExistsExceptionWhenCpfAlreadyExists() {
            Long id = 10L;
            UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
            userUpdateDTO.setCpf("12345678910");

            User user = new User();
            user.setId(10L);

            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            when(userRepository.existsByCpfAndIdNot(userUpdateDTO.getCpf(), user.getId())).thenReturn(true);

            assertThrows(UserAlreadyExistsException.class, () -> {
               userService.update(id, userUpdateDTO);
            });

            verify(userRepository, never()).save(any());
            verify(userMapper, never()).convertUserToResponse(any());
            verify(userRepository).findById(id);
        }

        @Test
        void shouldReturnUserUpdatedSuccessfully() {
            Long id = 10L;
            UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
            userUpdateDTO.setName("Test");
            userUpdateDTO.setEmail("test@email.com");
            userUpdateDTO.setCpf("12345678910");
            userUpdateDTO.setPhoneNumber("41999999999");

            User user = new User();
            user.setId(10L);

            UserResponseDTO userResponseDTO = new UserResponseDTO(
                    10L,
                    "Test",
                    "test@email.com",
                    "12345678910",
                    "41999999999",
                    UserRole.USER,
                    "SYSTEM-V1",
                    Instant.now(),
                    Instant.now()
            );

            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            when(userRepository.existsByUsernameAndIdNot(userUpdateDTO.getEmail(), user.getId())).thenReturn(false);
            when(userRepository.existsByCpfAndIdNot(userUpdateDTO.getCpf(), user.getId())).thenReturn(false);
            when(userMapper.convertUserToResponse(user)).thenReturn(userResponseDTO);

            UserResponseDTO result = userService.update(id, userUpdateDTO);

            ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

            verify(userRepository).save(argumentCaptor.capture());
            verify(userRepository).findById(id);
            verify(userRepository).existsByUsernameAndIdNot(userUpdateDTO.getEmail(), user.getId());
            verify(userRepository).existsByCpfAndIdNot(userUpdateDTO.getCpf(), user.getId());
            verify(userMapper).convertUserToResponse(user);

            User userUpdated = argumentCaptor.getValue();

            assertEquals("Test", userUpdated.getName());
            assertEquals("test@email.com", userUpdated.getUsername());
            assertEquals("12345678910", userUpdated.getCpf());
            assertEquals("41999999999", userUpdated.getPhoneNumber());
            assertSame(userResponseDTO, result);
        }
    }

    @Nested
    class UpdatePassword {

        @Test
        void shouldThrowsUserNotFoundExceptionWhenUserNotFound() {
            Long id = 10L;
            PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();

            when(userRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> {
                userService.updatePassword(id, passwordUpdateDTO);
            });

            verify(userRepository).findById(id);
            verify(passwordEncoder, never()).matches(any(), any());
            verify(passwordEncoder, never()).encode(any());
            verify(userRepository, never()).save(any());
        }

        @Test
        void shouldThrowsWeakPasswordExceptionWhenNewPasswordIsWeak() {
            Long id = 10L;
            PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
            passwordUpdateDTO.setNewPassword("123");
            User user = new User();
            user.setUsername("test@email.com");

            when(userRepository.findById(id)).thenReturn(Optional.of(user));

            assertThrows(WeakPasswordException.class, () -> {
                userService.updatePassword(id, passwordUpdateDTO);
            });

            verify(userRepository).findById(id);
            verify(passwordEncoder, never()).matches(any(), any());
            verify(passwordEncoder, never()).encode(any());
            verify(userRepository, never()).save(any());
        }

        @Test
        void shouldThrowsInvalidCurrentPasswordExceptionWhenCurrentPasswordIsInvalid() {
            Long id = 10L;
            PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
            passwordUpdateDTO.setNewPassword("Password@123");
            passwordUpdateDTO.setCurrentPassword("oldPassword");

            User user = new User();
            user.setUsername("test@email.com");
            user.setPassword("encodedOldPassword");

            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(passwordUpdateDTO.getCurrentPassword(), user.getPassword())).thenReturn(false);

            assertThrows(InvalidCurrentPasswordException.class, () -> {
               userService.updatePassword(id, passwordUpdateDTO);
            });

            verify(userRepository).findById(id);
            verify(passwordEncoder, never()).encode(any());
            verify(userRepository, never()).save(any());
        }

        @Test
        void shouldUpdateUserPasswordSuccessfully() {
            Long id = 10L;
            PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
            passwordUpdateDTO.setNewPassword("Password@123");
            passwordUpdateDTO.setCurrentPassword("oldPassword");

            User user = new User();
            user.setUsername("test@email.com");
            user.setPassword("encodedOldPassword");

            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(passwordUpdateDTO.getCurrentPassword(), user.getPassword())).thenReturn(true);
            when(passwordEncoder.encode(passwordUpdateDTO.getNewPassword())).thenReturn("encodedNewPassword");

            userService.updatePassword(id, passwordUpdateDTO);

            ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

            verify(userRepository).save(argumentCaptor.capture());
            verify(userRepository).findById(id);
            verify(passwordEncoder).matches(passwordUpdateDTO.getCurrentPassword(), "encodedOldPassword");
            verify(passwordEncoder).encode(passwordUpdateDTO.getNewPassword());

            User userUpdated = argumentCaptor.getValue();

            assertEquals("encodedNewPassword", userUpdated.getPassword());
        }
    }

    @Nested
    class UpdateRole{

        @Test
        void shouldThrowsUserNotFoundExceptionWhenUserNotExists() {
            Long id = 10L;
            UserRoleUpdateDTO userRoleUpdateDTO = new UserRoleUpdateDTO();

            when(userRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> {
               userService.updateRole(id, userRoleUpdateDTO);
            });

            verify(userRepository).findById(id);
            verify(userRepository, never()).save(any());
        }

        @Test
        void shouldUpdateUserRoleSuccessfully() {
            Long id = 10L;
            UserRoleUpdateDTO userRoleUpdateDTO = new UserRoleUpdateDTO();
            userRoleUpdateDTO.setNewRole(UserRole.ADMIN);

            User user = new User();

            when(userRepository.findById(id)).thenReturn(Optional.of(user));

            userService.updateRole(id, userRoleUpdateDTO);

            ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

            verify(userRepository).findById(id);
            verify(userRepository).save(argumentCaptor.capture());

            User userUpdated = argumentCaptor.getValue();

            assertEquals(UserRole.ADMIN, userUpdated.getRole());
        }
    }

    @Nested
    class Delete {

        @Test
        void shouldThrowsUserNotFoundExceptionWhenUserNotExists() {
            Long id = 10L;

            when(userRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> {
               userService.deleteById(id);
            });

            verify(userRepository).findById(id);
            verify(userRepository, never()).deleteById(any());
        }

        @Test
        void shouldDeleteUserSuccessfully() {
            Long id = 10L;
            User user = new User();

            when(userRepository.findById(id)).thenReturn(Optional.of(user));

            userService.deleteById(id);

            verify(userRepository).findById(id);
            verify(userRepository).deleteById(id);
        }
    }

}
