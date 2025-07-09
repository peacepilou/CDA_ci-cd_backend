package com.backend_project_template.exceptions;

public class UserDomainException extends RuntimeException {
    public UserDomainException(String message) {
        super(message);
    }
}