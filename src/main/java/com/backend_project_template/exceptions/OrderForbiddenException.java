package com.backend_project_template.exceptions;

public class OrderForbiddenException extends RuntimeException {
    public OrderForbiddenException(String message) {
        super(message);
    }
}
