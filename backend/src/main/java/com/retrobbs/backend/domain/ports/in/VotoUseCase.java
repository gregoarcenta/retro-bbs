package com.retrobbs.backend.domain.ports.in;

import com.retrobbs.backend.domain.model.enums.TipoVoto;

public interface VotoUseCase {
    int votar(Long targetId, TipoVoto tipo, String username, String targetType);
    int quitarVoto(Long targetId, String username, String targetType);
}