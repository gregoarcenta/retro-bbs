package com.retrobbs.backend.domain.ports.out;

import com.retrobbs.backend.domain.model.RankingResult;

import java.util.List;

public interface RankingStore {
    void agregarPuntos(String username, double puntos);
    void quitarPuntos(String username, double puntos);
    List<RankingResult> obtenerTop10();
    Double obtenerPuntos(String username);
}