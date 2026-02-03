package com.example.apiestudo.config;

import com.example.apiestudo.enums.UserRole;
import com.example.apiestudo.model.User;
import com.example.apiestudo.repository.UserRepository;
import com.example.apiestudo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@ConfigurationProperties(prefix = "admin")
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

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
            user.setActive(true);
            user.setSystem("SYSTEM-V1");

            userRepository.save(user);
            logger.info("User admin created successfully.");
        } else {
            logger.info("User admin already exists.");
        }
    }
}
