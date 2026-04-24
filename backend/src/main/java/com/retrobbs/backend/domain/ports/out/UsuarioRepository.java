package com.retrobbs.backend.domain.ports.out;

import java.util.Optional;

public interface UsuarioRepository {
    Optional<String> buscarUsernamePorUsername(String username);
    boolean existePorUsername(String username);
    boolean existePorEmail(String email);
}