package com.retrobbs.backend.infrastructure.adapters.out.jpa.repository;

import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.VotoRespuestaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoRespuestaJpaRepository extends JpaRepository<VotoRespuestaEntity, Long> {
    Optional<VotoRespuestaEntity> findByUsuarioUsernameAndRespuestaId(String username, Long respuestaId);

    boolean existsByUsuarioUsernameAndRespuestaId(String username, Long respuestaId);

    int countByRespuestaId(Long respuestaId);
}