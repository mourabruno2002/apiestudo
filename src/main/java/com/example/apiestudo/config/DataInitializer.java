package com.example.apiestudo.config;

import com.example.apiestudo.enums.UserRole;
import com.example.apiestudo.model.User;
import com.example.apiestudo.repository.UserRepository;
import com.example.apiestudo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> userFounded = userRepository.findByUsername("admin@admin.com");

        if (userFounded.isEmpty()) {
            User user = new User();

            user.setName("admin");
            user.setUsername("admin@admin.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(UserRole.ADMIN);

            userRepository.save(user);
            System.out.println("User admin created successfully.");
        } else {
            System.out.println("User admin already exists.");
        }
    }
}
