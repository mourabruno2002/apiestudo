package com.example.apiestudo.service;

import com.example.apiestudo.dto.auth.UserRoleUpdateDTO;
import com.example.apiestudo.dto.user.*;
import com.example.apiestudo.enums.UserRole;
import com.example.apiestudo.exception.domain.user.InvalidCurrentPasswordException;
import com.example.apiestudo.exception.domain.user.UserAlreadyExistsException;
import com.example.apiestudo.exception.domain.user.UserNotFoundException;
import com.example.apiestudo.exception.domain.user.WeakPasswordException;
import com.example.apiestudo.mapper.UserMapper;
import com.example.apiestudo.model.User;
import com.example.apiestudo.repository.UserRepository;
import com.example.apiestudo.specification.UserSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private static final String WEAK_PASSWORD_MESSAGE =
            "Weak password. The provided password does not meet the minimum security requirements.";

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void create(UserRequestDTO userRequestDTO) {
        User newUser = userMapper.convertRequestToUser(userRequestDTO);

        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.",
                    newUser.getUsername()));
        }

        if (userRepository.findByCpf(newUser.getCpf()).isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with CPF %s already exists.", newUser.getCpf()));
        }

        validatePassword(newUser.getPassword(), newUser.getUsername());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
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
    public Page<UserResponseDTO> search(UserFilterDTO userFilterDTO, Pageable pageable) {
        Specification<User> spec = Specification.where(null);
        spec = spec.and(UserSpecification.isActive(userFilterDTO.getActive()));
        spec = spec.and(UserSpecification.nameContaining(userFilterDTO.getName()));
        spec = spec.and(UserSpecification.hasRole(userFilterDTO.getRole()));

        return userRepository.findAll(spec, pageable).map(userMapper::convertUserToResponse);
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
                    throw new UserAlreadyExistsException(String.format("User with email %s already exists.",
                            userUpdateDTO.getEmail()));
                }

                existing.setUsername(userUpdateDTO.getEmail());
            }
        }

        if (userUpdateDTO.getCpf() != null) {
            if (!Objects.equals(userUpdateDTO.getCpf(), existing.getCpf())) {
                if (userRepository.existsByCpfAndIdNot(userUpdateDTO.getCpf(), existing.getId())) {
                    throw new UserAlreadyExistsException(String.format("User with CPF %s already exists.",
                            userUpdateDTO.getCpf()));
                }

                existing.setCpf(userUpdateDTO.getCpf());
            }
        }

        if (userUpdateDTO.getPhoneNumber() != null) {
            existing.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        }

        userRepository.save(existing);

        return userMapper.convertUserToResponse(existing);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Transactional
    public void updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO) {
        User foundUser = getUserById(id);

        validatePassword(passwordUpdateDTO.getNewPassword(), foundUser.getUsername());

        if (!passwordEncoder.matches(passwordUpdateDTO.getCurrentPassword(), foundUser.getPassword())) {
            throw new InvalidCurrentPasswordException("The current password is incorrect.");
        }

        foundUser.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
        userRepository.save(foundUser);
    }

    @PreAuthorize("hasRole('ADMIN') and #id != authentication.principal.id")
    @Transactional
    public void updateRole(Long id, UserRoleUpdateDTO userRoleUpdateDTO) {
        User foundUser = getUserById(id);

        foundUser.setRole(userRoleUpdateDTO.getNewRole());

        userRepository.save(foundUser);
    }

    @PreAuthorize("(hasRole('ADMIN') and #id != authentication.principal.id) or (hasRole('USER') " +
            "and #id == authentication.principal.id)")
    @Transactional
    public void deleteById(Long id) {
        getUserById(id);

        userRepository.deleteById(id);
    }


    // INTERNAL METHODS
    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with ID %d not found.", id))
        );
    }

    private void validatePassword(String password, String username) {
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
