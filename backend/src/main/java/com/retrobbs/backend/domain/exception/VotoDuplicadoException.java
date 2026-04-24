package com.retrobbs.backend.domain.exception;

import org.springframework.http.HttpStatus;

public class VotoDuplicadoException extends AppException {
    public VotoDuplicadoException() {
        super(
                "DUPLICATE_VOTE",
                "Ya votaste en este contenido",
                HttpStatus.CONFLICT
        );
    }
}