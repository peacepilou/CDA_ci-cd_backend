package com.backend_project_template.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(Long userId) {
    super("User with ID " + userId + " not found.");
  }

  public UserNotFoundException(String userEmail) {
    super("User with ID " + userEmail + " not found.");
  }
}
