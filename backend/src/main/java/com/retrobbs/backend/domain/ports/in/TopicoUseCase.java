package com.retrobbs.backend.domain.ports.in;

import com.retrobbs.backend.domain.model.TopicoResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicoUseCase {
    TopicoResult crear(String title, String content, Long categoriaId, String username);
    TopicoResult obtenerPorId(Long id, String username);
    Page<TopicoResult> listar(Long categoriaId, Pageable pageable);
    TopicoResult actualizar(Long id, String title, String content, String username);
    void eliminar(Long id, String username);
}