package com.retrobbs.backend.infrastructure.adapters.out.jpa.entity;

import com.retrobbs.backend.domain.model.enums.TipoVoto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "votos_respuestas",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "response_id"}
        ))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class VotoRespuestaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id", nullable = false)
    private RespuestaEntity respuesta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVoto type;
}