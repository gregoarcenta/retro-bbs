package com.retrobbs.backend.infrastructure.adapters.out.jpa.entity;

import com.retrobbs.backend.domain.model.enums.EstadoTopico;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TopicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoTopico status = EstadoTopico.ABIERTO;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UsuarioEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoriaEntity categoria;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<RespuestaEntity> respuestas;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<VotoTopicoEntity> votos;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}