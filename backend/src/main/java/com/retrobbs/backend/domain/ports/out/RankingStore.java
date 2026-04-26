package com.retrobbs.backend.domain.ports.out;

import com.retrobbs.backend.domain.model.RankingResult;

import java.time.LocalDate;
import java.util.List;

public interface RankingStore {
    void agregarPuntos(String username, double puntos);
    void quitarPuntos(String username, double puntos);
    List<RankingResult> obtenerTop10();
    List<RankingResult> obtenerTop10DelMes(LocalDate mes); // ← nuevo
    Double obtenerPuntos(String username);
}