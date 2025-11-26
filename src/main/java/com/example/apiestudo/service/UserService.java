package com.example.apiestudo.service;

import com.example.apiestudo.dto.user.PasswordUpdateDTO;
import com.example.apiestudo.dto.user.UserRequestDTO;
import com.example.apiestudo.dto.user.UserResponseDTO;
import com.example.apiestudo.dto.user.UserUpdateDTO;
import com.example.apiestudo.exception.user.InvalidCurrentPasswordException;
import com.example.apiestudo.exception.user.UserAlreadyExistsException;
import com.example.apiestudo.exception.user.UserNotFoundException;
import com.example.apiestudo.mapper.UserMapper;
import com.example.apiestudo.model.User;
import com.example.apiestudo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        User newUser = userMapper.convertRequestToUser(userRequestDTO);

        if (userRepository.findByEmail(newUser.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", newUser.getUsername()));
        }
        newUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        newUser.setRole("USER");

        return userMapper.convertUserToResponse(userRepository.save(newUser));
    }

    public UserResponseDTO findById(Long id) {
        return userMapper.convertUserToResponse(getUserById(id));
    }

    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::convertUserToResponse);
    }

    public UserResponseDTO findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::convertUserToResponse).orElseThrow(
                () -> new UserNotFoundException(String.format("User with email %s not found.", email))
        );
    }

    public Page<UserResponseDTO> search(String email, Pageable pageable) {

        Optional<User> foundUser = userRepository.findByEmail(email);
        List<UserResponseDTO> list = new ArrayList<>();

        foundUser.ifPresent(User -> list.add(userMapper.convertUserToResponse(foundUser.get())));

        return new PageImpl<>(list, pageable, list.size());
    }

    public UserResponseDTO update(Long id, UserUpdateDTO userUpdateDTO) {
        User existing = getUserById(id);

        if (userUpdateDTO.getName() != null) {
            existing.setName(userUpdateDTO.getName());
        }

        if (userUpdateDTO.getEmail() != null) {

            if (!Objects.equals(userUpdateDTO.getEmail(), existing.getUsername())) {

                if (userRepository.existsByEmailAndIdNot(userUpdateDTO.getEmail(), existing.getId())) {
                    throw new UserAlreadyExistsException(String.format("User with email %s already exists.", userUpdateDTO.getEmail()));

                } else {
                    existing.setUsername(userUpdateDTO.getEmail());
                }
            }
        }

        userRepository.save(existing);

        return userMapper.convertUserToResponse(existing);
    }

    public void updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO) {
        User foundUser = getUserById(id);
        if (passwordEncoder.matches(passwordUpdateDTO.getCurrentPassword(), foundUser.getPassword())) {
            foundUser.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
            userRepository.save(foundUser);
        } else {
            throw new InvalidCurrentPasswordException("The current password is incorrect.");
        }
    }

    public void deleteById(Long id) {

        userRepository.deleteById(id);
    }

    // INTERNAL METHODS
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with ID %d not found.", id))
        );
    }

    public Optional<User> findEntityByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
