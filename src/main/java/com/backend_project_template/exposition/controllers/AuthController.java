package com.backend_project_template.exposition.controllers;

import com.backend_project_template.domain.User;
import com.backend_project_template.exposition.dtos.CreateUserDto;
import com.backend_project_template.exposition.dtos.UserReponseDto;
import com.backend_project_template.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserReponseDto> register(@RequestBody CreateUserDto request) {
        User user = authService.createUser(request);
        UserReponseDto response = new UserReponseDto(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users/by-email")
    public User getUserByEmail(@RequestParam String email) {
        return authService.getUserByEmail(email);
    }
}
