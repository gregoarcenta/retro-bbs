package com.retrobbs.backend.infrastructure.adapters.out.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "perfiles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PerfilEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UsuarioEntity usuario;

    @Column(length = 300)
    private String bio;

    @Column(name = "avatar_pixel", columnDefinition = "TEXT")
    private String avatarPixel; // JSON con el pixel art

    @Column(nullable = false)
    @Builder.Default
    private Integer points = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer level = 1;

    @ManyToMany
    @JoinTable(
            name = "perfil_logro",
            joinColumns = @JoinColumn(name = "perfil_id"),
            inverseJoinColumns = @JoinColumn(name = "logro_id")
    )
    private List<LogroEntity> logros;
}