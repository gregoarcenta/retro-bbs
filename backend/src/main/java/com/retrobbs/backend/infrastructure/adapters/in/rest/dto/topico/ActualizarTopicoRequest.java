package com.retrobbs.backend.infrastructure.adapters.in.rest.dto.topico;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActualizarTopicoRequest {

    @Size(min = 5, max = 150, message = "El título debe tener entre 5 y 150 caracteres")
    private String title;

    @Size(min = 10, max = 5000, message = "El contenido debe tener entre 10 y 5000 caracteres")
    private String content;
}