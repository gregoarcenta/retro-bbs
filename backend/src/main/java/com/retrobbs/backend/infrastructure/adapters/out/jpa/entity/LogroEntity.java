package com.retrobbs.backend.infrastructure.adapters.out.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "logros")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LogroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 80)
    private String name;

    @Column(name = "icon_code", length = 20)
    private String iconCode; // Ej: "★", "♛", "▲"

    @Column(length = 200)
    private String description;

    @ManyToMany(mappedBy = "logros")
    private List<PerfilEntity> perfiles;
}