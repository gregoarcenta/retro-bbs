package com.retrobbs.backend.infrastructure.adapters.in.rest.dto.topico;

import com.retrobbs.backend.domain.model.enums.EstadoTopico;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TopicoResponse {
    private Long id;
    private String title;
    private String content;
    private EstadoTopico status;
    private String authorUsername;
    private String categoriaNombre;
    private int totalRespuestas;
    private int totalVotos;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}