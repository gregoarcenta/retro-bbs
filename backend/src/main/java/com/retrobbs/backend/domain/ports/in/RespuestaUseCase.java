package com.retrobbs.backend.domain.ports.in;

import com.retrobbs.backend.domain.model.RespuestaResult;

import java.util.List;

public interface RespuestaUseCase {
    RespuestaResult crear(Long topicoId, String content, String username);
    List<RespuestaResult> listarPorTopico(Long topicoId);
    RespuestaResult marcarComoSolucion(Long respuestaId, String username);
    void eliminar(Long respuestaId, String username);
}