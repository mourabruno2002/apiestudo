package com.example.apiestudo.bootstrap;

import com.example.apiestudo.config.AdminProperties;
import com.example.apiestudo.enums.UserRole;
import com.example.apiestudo.model.User;
import com.example.apiestudo.repository.UserRepository;
import com.example.apiestudo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;
    private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    public DataInitializer(PasswordEncoder passwordEncoder, UserRepository userRepository, AdminProperties adminProperties) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminProperties = adminProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> userFounded = userRepository.findByUsername(adminProperties.getUsername());

        if (userFounded.isEmpty()) {
            User user = new User();

            user.setName("admin");
            user.setUsername(adminProperties.getUsername());
            user.setPassword(passwordEncoder.encode(adminProperties.getPassword()));
            user.setRole(adminProperties.getRole());
            user.setActive(true);
            user.setSystem("SYSTEM-V1");

            userRepository.save(user);
            logger.info("User admin created successfully.");
        } else {
            logger.info("User admin already exists.");
        }
    }
}
