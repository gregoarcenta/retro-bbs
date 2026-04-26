package com.retrobbs.backend.infrastructure.adapters.out.jpa.adapter;

import com.retrobbs.backend.domain.model.TopicoResult;
import com.retrobbs.backend.domain.model.enums.EstadoTopico;
import com.retrobbs.backend.domain.model.enums.TipoVoto;
import com.retrobbs.backend.domain.ports.out.TopicoRepository;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.CategoriaEntity;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.TopicoEntity;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.UsuarioEntity;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.repository.CategoriaJpaRepository;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.repository.TopicoJpaRepository;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.repository.UsuarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TopicoRepositoryAdapter implements TopicoRepository {

    private final TopicoJpaRepository topicoJpaRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;
    private final CategoriaJpaRepository categoriaJpaRepository;

    @Override
    public TopicoResult guardar(String title, String content,
                                Long categoriaId, String username) {

        UsuarioEntity autor = usuarioJpaRepository.findByUsername(username)
                .orElseThrow();
        CategoriaEntity categoria = categoriaJpaRepository.findById(categoriaId)
                .orElseThrow();

        TopicoEntity entity = TopicoEntity.builder()
                .title(title)
                .content(content)
                .author(autor)
                .categoria(categoria)
                .status(EstadoTopico.ABIERTO)
                .build();

        return toResult(topicoJpaRepository.save(entity));
    }

    @Override
    public Optional<TopicoResult> buscarPorId(Long id) {
        return topicoJpaRepository.findById(id).map(this::toResult);
    }

    @Override
    public Page<TopicoResult> listarPorEstado(EstadoTopico estado, Pageable pageable) {
        return topicoJpaRepository
                .findByStatusOrderByCreatedAtDesc(estado, pageable)
                .map(this::toResult);
    }

    @Override
    public Page<TopicoResult> listarPorCategoria(Long categoriaId, Pageable pageable) {
        return topicoJpaRepository
                .findByCategoriaIdOrderByCreatedAtDesc(categoriaId, pageable)
                .map(this::toResult);
    }

    @Override
    public TopicoResult actualizar(Long id, String title, String content) {
        TopicoEntity entity = topicoJpaRepository.findById(id).orElseThrow();
        if (title != null)   entity.setTitle(title);
        if (content != null) entity.setContent(content);
        return toResult(topicoJpaRepository.save(entity));
    }

    @Override
    public void cambiarEstado(Long id, EstadoTopico estado) {
        TopicoEntity entity = topicoJpaRepository.findById(id).orElseThrow();
        entity.setStatus(estado);
        topicoJpaRepository.save(entity);
    }

    @Override
    public boolean esAutorDelTopico(Long topicoId, String username) {
        return topicoJpaRepository.findById(topicoId)
                .map(t -> t.getAuthor().getUsername().equals(username))
                .orElse(false);
    }

    private TopicoResult toResult(TopicoEntity t) {
        int totalVotos = t.getVotos() == null ? 0 :
                t.getVotos().stream()
                .mapToInt(v -> v.getType() == TipoVoto.UP ? 1 : -1)
                .sum();

        return TopicoResult.builder()
                .id(t.getId())
                .title(t.getTitle())
                .content(t.getContent())
                .status(t.getStatus())
                .authorUsername(t.getAuthor().getUsername())
                .categoriaNombre(t.getCategoria() != null
                                         ? t.getCategoria().getName() : null)
                .totalRespuestas(t.getRespuestas() == null
                                         ? 0 : t.getRespuestas().size())
                .totalVotos(totalVotos)
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .build();
    }
}