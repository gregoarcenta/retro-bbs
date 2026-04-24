package com.retrobbs.backend.domain.ports.in;

import com.retrobbs.backend.domain.model.RankingResult;

import java.util.List;

public interface RankingUseCase {
    List<RankingResult> obtenerTop10();
    void agregarPuntos(String username, double puntos);
    Double obtenerPuntosUsuario(String username);
}