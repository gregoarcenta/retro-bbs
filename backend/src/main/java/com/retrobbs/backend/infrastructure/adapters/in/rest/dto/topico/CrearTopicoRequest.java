package com.retrobbs.backend.infrastructure.adapters.in.rest.dto.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrearTopicoRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 5, max = 150, message = "El título debe tener entre 5 y 150 caracteres")
    private String title;

    @NotBlank(message = "El contenido es obligatorio")
    @Size(min = 10, max = 5000, message = "El contenido debe tener entre 10 y 5000 caracteres")
    private String content;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;
}