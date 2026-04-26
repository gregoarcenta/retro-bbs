package com.retrobbs.backend.domain.model;

import com.retrobbs.backend.domain.model.enums.EstadoTopico;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TopicoResult {
    private Long id;
    private String title;
    private String content;
    private EstadoTopico status;
    private String authorUsername;
    private String categoriaNombre;
    private int totalRespuestas;
    private int totalVotos;
    private String miVoto;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TopicoResult conMiVoto(String miVoto) {
        return TopicoResult.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .status(this.status)
                .authorUsername(this.authorUsername)
                .categoriaNombre(this.categoriaNombre)
                .totalRespuestas(this.totalRespuestas)
                .totalVotos(this.totalVotos)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .miVoto(miVoto)
                .build();
    }
}