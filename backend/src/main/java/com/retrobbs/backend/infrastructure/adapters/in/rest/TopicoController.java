package com.retrobbs.backend.infrastructure.adapters.in.rest;

import com.retrobbs.backend.domain.model.TopicoResult;
import com.retrobbs.backend.domain.ports.in.TopicoUseCase;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.common.ApiResponse;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.topico.ActualizarTopicoRequest;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.topico.CrearTopicoRequest;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.topico.TopicoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topicos")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoUseCase topicoUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<TopicoResponse>> crear(
            @Valid @RequestBody CrearTopicoRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        TopicoResult result = topicoUseCase.crear(request.getTitle(),
                                                  request.getContent(),
                                                  request.getCategoriaId(),
                                                  userDetails.getUsername()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Tópico creado correctamente", toResponse(result)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TopicoResponse>>> listar(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<TopicoResponse> response = topicoUseCase.listar(categoriaId, PageRequest.of(page, size))
                .map(this::toResponse);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TopicoResponse>> obtener(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails != null ? userDetails.getUsername() : null;

        TopicoResult result = topicoUseCase.obtenerPorId(id, username);

        return ResponseEntity.ok(ApiResponse.ok(toResponse(result)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TopicoResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarTopicoRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        TopicoResult result = topicoUseCase.actualizar(id,
                                                       request.getTitle(),
                                                       request.getContent(),
                                                       userDetails.getUsername()
        );

        return ResponseEntity.ok(ApiResponse.ok("Tópico actualizado", toResponse(result)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        topicoUseCase.eliminar(id, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.ok("Tópico eliminado", null));
    }

    private TopicoResponse toResponse(TopicoResult result) {
        return TopicoResponse.builder()
                .id(result.getId())
                .title(result.getTitle())
                .content(result.getContent())
                .status(result.getStatus())
                .authorUsername(result.getAuthorUsername())
                .categoriaNombre(result.getCategoriaNombre())
                .totalRespuestas(result.getTotalRespuestas())
                .totalVotos(result.getTotalVotos())
                .miVoto(result.getMiVoto())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .build();
    }
}