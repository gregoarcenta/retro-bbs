package com.retrobbs.backend.infrastructure.adapters.in.rest.dto.voto;

import com.retrobbs.backend.domain.model.enums.TipoVoto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VotoRequest {

    @NotNull(message = "El tipo de voto es obligatorio")
    private TipoVoto tipo; // UP o DOWN

    @NotNull(message = "El tipo de target es obligatorio")
    private String targetType; // "TOPICO" o "RESPUESTA"
}