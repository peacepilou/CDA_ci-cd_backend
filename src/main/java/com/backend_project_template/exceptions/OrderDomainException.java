package com.backend_project_template.exceptions;

public class OrderDomainException extends RuntimeException {

  public OrderDomainException(String message) {
    super(message);
  }
}
