package com.retrobbs.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingResult {
    private int posicion;
    private String username;
    private double puntos;
    private String nivel;
}