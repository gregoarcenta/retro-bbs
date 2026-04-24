package com.retrobbs.backend.infrastructure.adapters.in.rest.dto.ranking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingItemResponse {
    private int posicion;
    private String username;
    private double puntos;
    private String nivel; // "NOVATO", "REGULAR", "EXPERTO", "LEYENDA"
}