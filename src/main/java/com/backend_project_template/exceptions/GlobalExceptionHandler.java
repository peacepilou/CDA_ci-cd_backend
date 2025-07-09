package com.backend_project_template.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UserNotFoundException.class,
            OrderNotFoundException.class,
    })
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body("Item not found: " + ex.getMessage());
    }

    @ExceptionHandler({
            OrderDomainException.class,
            UserDomainException.class,
            OrderItemDomainException.class
    })
    public ResponseEntity<String> handleDomainException(RuntimeException ex) {
        return ResponseEntity.unprocessableEntity().body(ex.getMessage());
    }

    @ExceptionHandler(OrderForbiddenException.class)
    public ResponseEntity<String> handleOrderForbiddenException(OrderForbiddenException ex) {
        return ResponseEntity
                .status(403)
                .body("Access denied: " + ex.getMessage());
    }

}
