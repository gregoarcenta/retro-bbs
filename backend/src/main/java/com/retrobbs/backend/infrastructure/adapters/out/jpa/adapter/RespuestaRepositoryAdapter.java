package com.retrobbs.backend.infrastructure.adapters.out.jpa.adapter;

import com.retrobbs.backend.domain.exception.ResourceNotFoundException;
import com.retrobbs.backend.domain.model.RespuestaResult;
import com.retrobbs.backend.domain.ports.out.RespuestaRepository;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.RespuestaEntity;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.TopicoEntity;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.UsuarioEntity;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.repository.RespuestaJpaRepository;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.repository.TopicoJpaRepository;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.repository.UsuarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RespuestaRepositoryAdapter implements RespuestaRepository {

    private final RespuestaJpaRepository respuestaJpaRepository;
    private final TopicoJpaRepository topicoJpaRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;

    @Override
    public RespuestaResult guardar(Long topicoId, String content, String username) {
        TopicoEntity topico = topicoJpaRepository.findById(topicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Topico", topicoId));

        UsuarioEntity autor = usuarioJpaRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", 0L));

        RespuestaEntity entity = RespuestaEntity.builder()
                .content(content)
                .topico(topico)
                .author(autor)
                .isSolution(false)
                .build();

        return toResult(respuestaJpaRepository.save(entity));
    }

    @Override
    public Optional<RespuestaResult> buscarPorId(Long id) {
        return respuestaJpaRepository.findById(id).map(this::toResult);
    }

    @Override
    public List<RespuestaResult> listarPorTopico(Long topicoId) {
        return respuestaJpaRepository
                .findByTopicoIdOrderByCreatedAtAsc(topicoId)
                .stream()
                .map(this::toResult)
                .toList();
    }

    @Override
    public RespuestaResult marcarComoSolucion(Long id) {
        RespuestaEntity entity = respuestaJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta", id));

        entity.setIsSolution(true);
        return toResult(respuestaJpaRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        respuestaJpaRepository.deleteById(id);
    }

    private RespuestaResult toResult(RespuestaEntity e) {
        int totalVotos = e.getVotos() == null ? 0 :
                e.getVotos().stream()
                .mapToInt(v -> v.getType() ==
                               com.retrobbs.backend.domain.model.enums.TipoVoto.UP ? 1 : -1)
                .sum();

        return RespuestaResult.builder()
                .id(e.getId())
                .content(e.getContent())
                .authorUsername(e.getAuthor().getUsername())
                .topicoId(e.getTopico().getId())
                .isSolution(e.getIsSolution())
                .totalVotos(totalVotos)
                .createdAt(e.getCreatedAt())
                .build();
    }
}