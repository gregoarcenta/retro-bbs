package com.retrobbs.backend.domain.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends AppException {
    public DuplicateResourceException(String field, String value) {
        super(
                field.toUpperCase() + "_TAKEN",
                "El " + field + " '" + value + "' ya está en uso",
                HttpStatus.CONFLICT
        );
    }
}