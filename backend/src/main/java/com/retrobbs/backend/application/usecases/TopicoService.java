package com.retrobbs.backend.application.usecases;

import com.retrobbs.backend.domain.exception.ForbiddenException;
import com.retrobbs.backend.domain.exception.ResourceNotFoundException;
import com.retrobbs.backend.domain.model.TopicoResult;
import com.retrobbs.backend.domain.model.enums.EstadoTopico;
import com.retrobbs.backend.domain.ports.in.TopicoUseCase;
import com.retrobbs.backend.domain.ports.out.TopicoRepository;
import com.retrobbs.backend.domain.ports.out.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicoService implements TopicoUseCase {

    private final TopicoRepository topicoRepository;
    private final RankingService rankingService;
    private final VotoRepository votoRepository;

    @Override
    @Transactional
    public TopicoResult crear(String title, String content,
                              Long categoriaId, String username) {

        TopicoResult resultado = topicoRepository.guardar(
                title, content, categoriaId, username
        );
        rankingService.agregarPuntos(username, RankingService.PUNTOS_TOPICO);
        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public TopicoResult obtenerPorId(Long id, String username) {
        TopicoResult topico = topicoRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topico", id));

        if (username != null) {
            String miVoto = votoRepository
                    .obtenerVotoDelUsuario(id, username, "TOPICO");
            return topico.conMiVoto(miVoto);
        }

        return topico;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicoResult> listar(Long categoriaId, Pageable pageable) {
        return categoriaId != null
                ? topicoRepository.listarPorCategoria(categoriaId, pageable)
                : topicoRepository.listarPorEstado(EstadoTopico.ABIERTO, pageable);
    }

    @Override
    @Transactional
    public TopicoResult actualizar(Long id, String title,
                                   String content, String username) {

        TopicoResult topico = topicoRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topico", id));

        if (!topico.getAuthorUsername().equals(username)) {
            throw new ForbiddenException("Solo el autor puede editar este tópico");
        }

        return topicoRepository.actualizar(id, title, content);
    }

    @Override
    @Transactional
    public void eliminar(Long id, String username) {
        TopicoResult topico = topicoRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topico", id));

        if (!topico.getAuthorUsername().equals(username)) {
            throw new ForbiddenException("Solo el autor puede eliminar este tópico");
        }

        topicoRepository.cambiarEstado(id, EstadoTopico.ELIMINADO);
        rankingService.agregarPuntos(username, -RankingService.PUNTOS_TOPICO);
    }
}