package com.retrobbs.backend.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RespuestaResult {
    private Long id;
    private String content;
    private String authorUsername;
    private Long topicoId;
    private Boolean isSolution;
    private int totalVotos;
    private LocalDateTime createdAt;
}