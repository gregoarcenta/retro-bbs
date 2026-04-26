package com.retrobbs.backend.application.usecases;

import com.retrobbs.backend.domain.exception.ForbiddenException;
import com.retrobbs.backend.domain.exception.ResourceNotFoundException;
import com.retrobbs.backend.domain.model.RespuestaResult;
import com.retrobbs.backend.domain.ports.in.RespuestaUseCase;
import com.retrobbs.backend.domain.ports.out.RespuestaRepository;
import com.retrobbs.backend.domain.ports.out.TopicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RespuestaService implements RespuestaUseCase {

    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;
    private final RankingService rankingService;

    @Override
    @Transactional
    public RespuestaResult crear(Long topicoId, String content, String username) {
        RespuestaResult resultado = respuestaRepository.guardar(topicoId, content, username);

        // Sumar puntos por responder
        rankingService.agregarPuntos(username, RankingService.PUNTOS_RESPUESTA);
        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RespuestaResult> listarPorTopico(Long topicoId) {
        return respuestaRepository.listarPorTopico(topicoId);
    }

    @Override
    @Transactional
    public RespuestaResult marcarComoSolucion(Long respuestaId, String username) {
        RespuestaResult respuesta = respuestaRepository.buscarPorId(respuestaId)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta", respuestaId));

        boolean esAutorDelTopico = topicoRepository.esAutorDelTopico(respuesta.getTopicoId(), username);

        if (!esAutorDelTopico) {
            throw new ForbiddenException(
                    "Solo el autor del tópico puede marcar una solución");
        }
        RespuestaResult resultado = respuestaRepository.marcarComoSolucion(respuestaId);

        // Bonus de puntos por solución correcta
        rankingService.agregarPuntos(respuesta.getAuthorUsername(), RankingService.PUNTOS_SOLUCION);
        return resultado;
    }

    @Override
    @Transactional
    public void eliminar(Long respuestaId, String username) {
        RespuestaResult respuesta = respuestaRepository.buscarPorId(respuestaId)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta", respuestaId));

        if (!respuesta.getAuthorUsername().equals(username)) {
            throw new ForbiddenException("Solo el autor puede eliminar su respuesta");
        }

        respuestaRepository.eliminar(respuestaId);

        // Quitar puntos al eliminar
        rankingService.agregarPuntos(username, -RankingService.PUNTOS_RESPUESTA);
    }
}