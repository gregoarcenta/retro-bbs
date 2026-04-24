package com.retrobbs.backend.infrastructure.adapters.out.jpa.repository;

import com.retrobbs.backend.domain.model.enums.EstadoTopico;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.TopicoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicoJpaRepository extends JpaRepository<TopicoEntity, Long> {
    Page<TopicoEntity> findByStatusOrderByCreatedAtDesc(EstadoTopico status, Pageable pageable);

    Page<TopicoEntity> findByCategoriaIdOrderByCreatedAtDesc(Long categoriaId, Pageable pageable);
}