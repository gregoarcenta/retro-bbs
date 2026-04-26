package com.retrobbs.backend.infrastructure.adapters.out.jpa.adapter;

import com.retrobbs.backend.domain.exception.ResourceNotFoundException;
import com.retrobbs.backend.domain.model.enums.TipoVoto;
import com.retrobbs.backend.domain.ports.out.VotoRepository;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.*;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VotoRepositoryAdapter implements VotoRepository {

    private final VotoTopicoJpaRepository votoTopicoRepository;
    private final VotoRespuestaJpaRepository votoRespuestaRepository;
    private final TopicoJpaRepository topicoJpaRepository;
    private final RespuestaJpaRepository respuestaJpaRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;

    @Override
    public int votar(Long targetId, TipoVoto tipo, String username, String targetType) {
        UsuarioEntity usuario = usuarioJpaRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", 0L));

        if (targetType.equals("TOPICO")) {
            TopicoEntity topico = topicoJpaRepository.findById(targetId)
                    .orElseThrow(() -> new ResourceNotFoundException("Topico", targetId));

            VotoTopicoEntity voto = VotoTopicoEntity.builder()
                    .usuario(usuario)
                    .topico(topico)
                    .type(tipo)
                    .build();

            votoTopicoRepository.save(voto);
            return votoTopicoRepository.countByTopicoId(targetId);

        } else {
            RespuestaEntity respuesta = respuestaJpaRepository.findById(targetId)
                    .orElseThrow(() -> new ResourceNotFoundException("Respuesta", targetId));

            VotoRespuestaEntity voto = VotoRespuestaEntity.builder()
                    .usuario(usuario)
                    .respuesta(respuesta)
                    .type(tipo)
                    .build();

            votoRespuestaRepository.save(voto);
            return votoRespuestaRepository.countByRespuestaId(targetId);
        }
    }

    @Override
    public int quitarVoto(Long targetId, String username, String targetType) {
        if (targetType.equals("TOPICO")) {
            votoTopicoRepository
                    .findByUsuarioUsernameAndTopicoId(username, targetId)
                    .ifPresent(votoTopicoRepository::delete);
            return votoTopicoRepository.countByTopicoId(targetId);
        } else {
            votoRespuestaRepository
                    .findByUsuarioUsernameAndRespuestaId(username, targetId)
                    .ifPresent(votoRespuestaRepository::delete);
            return votoRespuestaRepository.countByRespuestaId(targetId);
        }
    }

    @Override
    public boolean existeVoto(Long targetId, String username, String targetType) {
        if (targetType.equals("TOPICO")) {
            return votoTopicoRepository
                    .existsByUsuarioUsernameAndTopicoId(username, targetId);
        } else {
            return votoRespuestaRepository
                    .existsByUsuarioUsernameAndRespuestaId(username, targetId);
        }
    }

    @Override
    public String obtenerVotoDelUsuario(Long targetId, String username, String targetType) {
        if (targetType.equals("TOPICO")) {
            return votoTopicoRepository
                    .findByUsuarioUsernameAndTopicoId(username, targetId)
                    .map(v -> v.getType().name()) // "UP" o "DOWN"
                    .orElse(null);
        } else {
            return votoRespuestaRepository
                    .findByUsuarioUsernameAndRespuestaId(username, targetId)
                    .map(v -> v.getType().name())
                    .orElse(null);
        }
    }
}