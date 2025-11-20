package com.marzetti.peluqueria.config;

import com.marzetti.peluqueria.entity.User;
import com.marzetti.peluqueria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Check if admin user exists
            userRepository.findByUsername("admin").ifPresentOrElse(
                    existingAdmin -> {
                        log.info("Admin user exists. Updating role and password to ensure access...");
                        existingAdmin.setPassword(passwordEncoder.encode("admin123"));
                        existingAdmin.setRole(User.UserRole.ADMIN);
                        userRepository.save(existingAdmin);
                        log.info("Admin user updated successfully.");
                    },
                    () -> {
                        log.info("Creating default admin user...");
                        User admin = new User();
                        admin.setUsername("admin");
                        admin.setPassword(passwordEncoder.encode("admin123"));
                        admin.setEmail("admin@peluqueriamarzetti.com");
                        admin.setRole(User.UserRole.ADMIN);
                        admin.setCreatedAt(LocalDateTime.now());
                        userRepository.save(admin);
                        log.info("Admin user created successfully! Username: admin, Password: admin123");
                    });
        };
    }
}
