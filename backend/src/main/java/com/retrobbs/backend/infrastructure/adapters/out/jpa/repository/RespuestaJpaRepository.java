package com.retrobbs.backend.infrastructure.adapters.out.jpa.repository;

import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.RespuestaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RespuestaJpaRepository extends JpaRepository<RespuestaEntity, Long> {
    List<RespuestaEntity> findByTopicoIdOrderByCreatedAtAsc(Long topicoId);
}