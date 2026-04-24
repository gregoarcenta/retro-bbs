package com.retrobbs.backend.infrastructure.adapters.out.jpa.repository;

import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaJpaRepository extends JpaRepository<CategoriaEntity, Long> {
    boolean existsByName(String name);
}