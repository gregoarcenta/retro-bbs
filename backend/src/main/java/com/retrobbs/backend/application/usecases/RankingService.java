package com.retrobbs.backend.application.usecases;

import com.retrobbs.backend.domain.model.RankingResult;
import com.retrobbs.backend.domain.ports.in.RankingUseCase;
import com.retrobbs.backend.domain.ports.out.RankingStore;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService implements RankingUseCase {

    // Puntos del sistema
    public static final double PUNTOS_TOPICO     = 10.0;
    public static final double PUNTOS_RESPUESTA  = 5.0;
    public static final double PUNTOS_VOTO_UP    = 2.0;
    public static final double PUNTOS_VOTO_DOWN  = -1.0;
    public static final double PUNTOS_SOLUCION   = 15.0;
    private final RankingStore rankingStore;

    @Override
    public void agregarPuntos(String username, double puntos) {
        rankingStore.agregarPuntos(username, puntos);
    }

    @Override
    public List<RankingResult> obtenerTop10() {
        return rankingStore.obtenerTop10();
    }

    @Override
    public Double obtenerPuntosUsuario(String username) {
        Double puntos = rankingStore.obtenerPuntos(username);
        return puntos != null ? puntos : 0.0;
    }
}