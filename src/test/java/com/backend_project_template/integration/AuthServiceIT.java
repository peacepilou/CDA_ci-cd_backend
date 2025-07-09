package com.backend_project_template.integration;

import com.backend_project_template.exposition.dtos.CreateUserDto;
import com.backend_project_template.persistence.entities.UserEntity;
import com.backend_project_template.persistence.repositories.UserRepository;
import com.backend_project_template.services.AuthService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class AuthServiceIT {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateUserWithHashedPassword() {
        CreateUserDto dto = new CreateUserDto("alice@test.com", "1234", "Alice");
        authService.createUser(dto);

        UserEntity saved = userRepository.findByEmail("alice@test.com").orElseThrow();
        assertNotEquals("1234", saved.getPassword());
        assertTrue(saved.getPassword().startsWith("$2"));
    }

}
