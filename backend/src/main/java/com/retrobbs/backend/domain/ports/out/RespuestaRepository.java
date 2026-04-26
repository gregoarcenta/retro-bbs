package com.retrobbs.backend.domain.ports.out;

import com.retrobbs.backend.domain.model.RespuestaResult;
import java.util.List;
import java.util.Optional;

public interface RespuestaRepository {
    RespuestaResult guardar(Long topicoId, String content, String username);
    Optional<RespuestaResult> buscarPorId(Long id);
    List<RespuestaResult> listarPorTopico(Long topicoId);
    RespuestaResult marcarComoSolucion(Long id);
    void eliminar(Long id);
}