package com.example.apiestudo.bootstrap;

import com.example.apiestudo.config.AdminProperties;
import com.example.apiestudo.model.User;
import com.example.apiestudo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AdminProperties adminProperties;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    public DataInitializer(UserRepository userRepository, AdminProperties adminProperties, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.adminProperties = adminProperties;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (!userRepository.existsByUsername(adminProperties.getUsername())) {

            User user = new User();

            user.setUsername(adminProperties.getUsername());
            user.setPassword(passwordEncoder.encode(adminProperties.getPassword()));
            user.setRole(adminProperties.getRole());
            user.setActive(true);

            userRepository.save(user);
            logger.info("ADMIN created successfully");
        } else {
            logger.info("ADMIN already exists.");
        }
    }
}
