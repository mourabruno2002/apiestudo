package com.example.apiestudo.service;

import com.example.apiestudo.dto.auth.UserRoleDTO;
import com.example.apiestudo.dto.user.*;
import com.example.apiestudo.enums.UserRole;
import com.example.apiestudo.exception.user.*;
import com.example.apiestudo.mapper.UserMapper;
import com.example.apiestudo.model.User;
import com.example.apiestudo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private static final String WEAK_PASSWORD_MESSAGE = "Weak password. The provided password does not meet the minimum security requirements.";

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void create(UserRequestDTO userRequestDTO) {
        User newUser = userMapper.convertRequestToUser(userRequestDTO);

        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", newUser.getUsername()));
        }

        validatePassword(userRequestDTO.getPassword(), userRequestDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        newUser.setRole(UserRole.USER);
        newUser.setActive(true);
        newUser.setSystem("SYSTEM-V1");

        userRepository.save(newUser);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public UserResponseDTO findById(Long id) {
        return userMapper.convertUserToResponse(getUserById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::convertUserToResponse);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Transactional
    public UserResponseDTO update(Long id, UserUpdateDTO userUpdateDTO) {
        User existing = getUserById(id);

        if (userUpdateDTO.getName() != null) {
            existing.setName(userUpdateDTO.getName());
        }

        if (userUpdateDTO.getEmail() != null) {

            if (!Objects.equals(userUpdateDTO.getEmail(), existing.getUsername())) {

                if (userRepository.existsByUsernameAndIdNot(userUpdateDTO.getEmail(), existing.getId())) {
                    throw new UserAlreadyExistsException(String.format("User with email %s already exists.", userUpdateDTO.getEmail()));

                } else {
                    existing.setUsername(userUpdateDTO.getEmail());
                }
            }
        }

        userRepository.save(existing);

        return userMapper.convertUserToResponse(existing);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Transactional
    public void updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO) {
        User foundUser = getUserById(id);

        validatePassword(passwordUpdateDTO.getNewPassword(), foundUser.getUsername());

        if (passwordEncoder.matches(passwordUpdateDTO.getCurrentPassword(), foundUser.getPassword())) {
            foundUser.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
            userRepository.save(foundUser);
        } else {
            throw new InvalidCurrentPasswordException("The current password is incorrect.");
        }
    }

    @PreAuthorize("hasRole('ADMIN') and #id != authentication.principal.id")
    @Transactional
    public void updateRole(Long id, UserRoleDTO userRoleDTO) {
        User foundUser = getUserById(id);

        foundUser.setRole(userRoleDTO.getRole());

        userRepository.save(foundUser);
    }

    @PreAuthorize("(hasRole('ADMIN') and #id != authentication.principal.id) or (hasRole('USER') and #id == authentication.principal.id)")
    @Transactional
    public void deleteById(Long id) {
        getUserById(id);

        userRepository.deleteById(id);
    }



    // INTERNAL METHODS
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with ID %d not found.", id))
        );
    }

    public Optional<User> findEntityByEmail(String email) {
        return userRepository.findByUsername(email);
    }

    public void validatePassword(String password, String username) {
        if (password.equalsIgnoreCase(username)) {
            throw new WeakPasswordException(WEAK_PASSWORD_MESSAGE);
        }

        if (password.length() < 8) {
            throw new WeakPasswordException(WEAK_PASSWORD_MESSAGE);
        }

        if (!password.matches(".*[a-z].*")) {
            throw new WeakPasswordException(WEAK_PASSWORD_MESSAGE);
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new WeakPasswordException(WEAK_PASSWORD_MESSAGE);
        }

        if (!password.matches(".*[0-9].*")) {
            throw new WeakPasswordException(WEAK_PASSWORD_MESSAGE);
        }

        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            throw new WeakPasswordException(WEAK_PASSWORD_MESSAGE);
        }
    }

}
