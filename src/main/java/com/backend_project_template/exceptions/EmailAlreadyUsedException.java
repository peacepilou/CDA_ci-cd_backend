package com.backend_project_template.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {

  public EmailAlreadyUsedException(String message) {
    super(message);
  }
}
