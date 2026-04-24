package com.retrobbs.backend.infrastructure.adapters.out.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "respuestas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RespuestaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicoEntity topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UsuarioEntity author;

    @Column(name = "is_solution")
    @Builder.Default
    private Boolean isSolution = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "respuesta", cascade = CascadeType.ALL)
    private List<VotoRespuestaEntity> votos;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}