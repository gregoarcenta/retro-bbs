package com.retrobbs.backend.domain.ports.out;

import com.retrobbs.backend.domain.model.enums.TipoVoto;

public interface VotoRepository {
    // Retorna el nuevo total de votos del target
    int votar(Long targetId, TipoVoto tipo, String username, String targetType);
    int quitarVoto(Long targetId, String username, String targetType);
    boolean existeVoto(Long targetId, String username, String targetType);
    String obtenerVotoDelUsuario(Long targetId, String username, String targetType);
}