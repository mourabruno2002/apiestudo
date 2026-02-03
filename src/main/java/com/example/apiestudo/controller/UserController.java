package com.example.apiestudo.controller;

import com.example.apiestudo.dto.auth.UserRoleUpdateDTO;
import com.example.apiestudo.dto.user.PasswordUpdateDTO;
import com.example.apiestudo.dto.user.UserResponseDTO;
import com.example.apiestudo.dto.user.UserUpdateDTO;
import com.example.apiestudo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getUsers(Pageable pageable) {

        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {

        return ResponseEntity.ok(userService.update(id, userUpdateDTO));
    }

   @PatchMapping("/{id}/update-password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        userService.updatePassword(id, passwordUpdateDTO);

        return ResponseEntity.noContent().build();
   }

    @PatchMapping("/{id}/update-role")
    public ResponseEntity<Void> updateRole(@PathVariable Long id, @Valid @RequestBody UserRoleUpdateDTO userRoleUpdateDTO) {
        userService.updateRole(id, userRoleUpdateDTO);

        return ResponseEntity.noContent().build();
    }
}
