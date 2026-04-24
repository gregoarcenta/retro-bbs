package com.retrobbs.backend.infrastructure.adapters.out.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 80)
    private String name;

    @Column(length = 200)
    private String description;

    // Ícono en texto ASCII para la UI de terminal
    @Column(name = "ascii_icon", length = 10)
    private String asciiIcon;
}