package com.retrobbs.backend.domain.ports.in;

import com.retrobbs.backend.domain.model.RankingResult;

import java.time.LocalDate;
import java.util.List;

public interface RankingUseCase {
    List<RankingResult> obtenerTop10();
    List<RankingResult> obtenerTop10DelMes(LocalDate mes);
    void agregarPuntos(String username, double puntos);
    Double obtenerPuntosUsuario(String username);
}