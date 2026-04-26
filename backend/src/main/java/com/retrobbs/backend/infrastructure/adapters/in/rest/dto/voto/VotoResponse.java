package com.retrobbs.backend.infrastructure.adapters.in.rest.dto.voto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VotoResponse {
    private String mensaje;
    private int nuevoTotal;
    private String targetType;
    private Long targetId;
}