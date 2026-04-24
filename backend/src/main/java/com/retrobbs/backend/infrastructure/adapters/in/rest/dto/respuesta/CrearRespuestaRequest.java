package com.retrobbs.backend.infrastructure.adapters.in.rest.dto.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrearRespuestaRequest {

    @NotBlank(message = "El contenido es obligatorio")
    @Size(min = 5, max = 3000, message = "La respuesta debe tener entre 5 y 3000 caracteres")
    private String content;
}