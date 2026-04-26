package com.retrobbs.backend.application.usecases;

import com.retrobbs.backend.domain.exception.VotoDuplicadoException;
import com.retrobbs.backend.domain.model.enums.TipoVoto;
import com.retrobbs.backend.domain.ports.in.VotoUseCase;
import com.retrobbs.backend.domain.ports.out.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VotoService implements VotoUseCase {

    private final VotoRepository votoRepository;
    private final RankingService rankingService;

    @Override
    @Transactional
    public int votar(Long targetId, TipoVoto tipo, String username, String targetType) {

        // no se puede votar dos veces
        if (votoRepository.existeVoto(targetId, username, targetType)) {
            throw new VotoDuplicadoException();
        }

        int nuevoTotal = votoRepository.votar(targetId, tipo, username, targetType);

        // Puntos por votar
        double puntos = tipo == TipoVoto.UP
                ? RankingService.PUNTOS_VOTO_UP
                : RankingService.PUNTOS_VOTO_DOWN;

        rankingService.agregarPuntos(username, puntos);
        return nuevoTotal;
    }

    @Override
    @Transactional
    public int quitarVoto(Long targetId, String username, String targetType) {
        int nuevoTotal = votoRepository.quitarVoto(targetId, username, targetType);

        // Quitar los puntos que se ganaron por votar
        rankingService.agregarPuntos(username, -RankingService.PUNTOS_VOTO_UP);
        return nuevoTotal;
    }
}