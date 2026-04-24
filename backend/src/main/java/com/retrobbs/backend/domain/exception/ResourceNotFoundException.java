package com.retrobbs.backend.domain.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AppException {
    public ResourceNotFoundException(String resource, Long id) {
        super(
                "RESOURCE_NOT_FOUND",
                resource + " con id " + id + " no encontrado",
                HttpStatus.NOT_FOUND
        );
    }
}