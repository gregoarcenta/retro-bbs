package com.retrobbs.backend.infrastructure.adapters.out.jpa.entity;

import com.retrobbs.backend.domain.model.enums.TipoVoto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "votos_topicos",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "topic_id"} // Un voto por usuario por tópico
        ))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class VotoTopicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicoEntity topico;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVoto type;
}