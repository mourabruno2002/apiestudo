package com.example.apiestudo.controller;

import com.example.apiestudo.dto.user.PasswordUpdateDTO;
import com.example.apiestudo.dto.user.UserRequestDTO;
import com.example.apiestudo.dto.user.UserResponseDTO;
import com.example.apiestudo.dto.user.UserUpdateDTO;
import com.example.apiestudo.mapper.UserMapper;
import com.example.apiestudo.model.User;
import com.example.apiestudo.service.UserService;
import com.example.apiestudo.utils.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userReceived = userService.createUser(userRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(userReceived);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getUsers(@RequestParam(required = false) String email ,Pageable pageable) {
        Page<UserResponseDTO> foundUsers;

        if (email == null) {
            foundUsers = userService.findAllUsers(pageable);
        } else {
            foundUsers = userService.searchUsers(email, pageable);
        }

        return ResponseEntity.status(HttpStatus.OK).body(foundUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

   @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UserResponseDTO userUpdated = userService.updateUser(id, userUpdateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
   }

   @PatchMapping("/change-password/{id}")
    public ResponseEntity<Void> changePassword(@Valid @PathVariable Long id, @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        userService.updatePassword(id, passwordUpdateDTO);

        return ResponseEntity.noContent().build();
   }

}
