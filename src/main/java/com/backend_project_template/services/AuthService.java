package com.backend_project_template.services;

import com.backend_project_template.domain.User;
import com.backend_project_template.exceptions.EmailAlreadyUsedException;
import com.backend_project_template.exceptions.UserNotFoundException;
import com.backend_project_template.exposition.dtos.CreateUserDto;
import com.backend_project_template.mappers.user.UserMapper;
import com.backend_project_template.persistence.entities.UserEntity;
import com.backend_project_template.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public User createUser(CreateUserDto request) {
    if (userRepository.existsByEmail(request.email())) {
      throw new EmailAlreadyUsedException("This email address cannot be used");
    }

    String hashedPassword = passwordEncoder.encode(request.password());
    User user = new User(request.name(), request.email(), hashedPassword);

    UserEntity userEntity = UserMapper.toEntity(user);
    UserEntity savedUser = userRepository.save(userEntity);
    return UserMapper.toDomain(savedUser);
  }

  public User getUserByEmail(String email) {
    return userRepository
      .findByEmail(email)
      .map(UserMapper::toDomain)
      .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
  }
}
