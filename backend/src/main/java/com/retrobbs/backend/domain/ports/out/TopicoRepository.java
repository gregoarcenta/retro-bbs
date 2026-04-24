// domain/ports/out/TopicoRepository.java
package com.retrobbs.backend.domain.ports.out;

import com.retrobbs.backend.domain.model.TopicoResult;
import com.retrobbs.backend.domain.model.enums.EstadoTopico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TopicoRepository {
    TopicoResult guardar(String title, String content, Long categoriaId, String username);

    Optional<TopicoResult> buscarPorId(Long id);

    Page<TopicoResult> listarPorEstado(EstadoTopico estado, Pageable pageable);

    Page<TopicoResult> listarPorCategoria(Long categoriaId, Pageable pageable);

    TopicoResult actualizar(Long id, String title, String content);

    void cambiarEstado(Long id, EstadoTopico estado);
}