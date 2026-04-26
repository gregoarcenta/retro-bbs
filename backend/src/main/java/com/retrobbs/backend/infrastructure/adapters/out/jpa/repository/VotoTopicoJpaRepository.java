package com.retrobbs.backend.infrastructure.adapters.out.jpa.repository;

import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.VotoTopicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoTopicoJpaRepository extends JpaRepository<VotoTopicoEntity, Long> {
    Optional<VotoTopicoEntity> findByUsuarioUsernameAndTopicoId(String username, Long topicoId);

    boolean existsByUsuarioUsernameAndTopicoId(String username, Long topicoId);

    int countByTopicoId(Long topicoId);
}