package com.retrobbs.backend.infrastructure.adapters.in.rest;

import com.retrobbs.backend.domain.model.RespuestaResult;
import com.retrobbs.backend.domain.ports.in.RespuestaUseCase;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.common.ApiResponse;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.respuesta.CrearRespuestaRequest;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.respuesta.RespuestaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topicos/{topicoId}/respuestas")
@RequiredArgsConstructor
public class RespuestaController {

    private final RespuestaUseCase respuestaUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<RespuestaResponse>> crear(
            @PathVariable Long topicoId,
            @Valid @RequestBody CrearRespuestaRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        RespuestaResult result = respuestaUseCase.crear(
                topicoId,
                request.getContent(),
                userDetails.getUsername()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Respuesta creada", toResponse(result)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RespuestaResponse>>> listar(
            @PathVariable Long topicoId) {

        List<RespuestaResponse> respuestas = respuestaUseCase
                .listarPorTopico(topicoId)
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(ApiResponse.ok(respuestas));
    }

    @PatchMapping("/{respuestaId}/solucion")
    public ResponseEntity<ApiResponse<RespuestaResponse>> marcarSolucion(
            @PathVariable Long topicoId,
            @PathVariable Long respuestaId,
            @AuthenticationPrincipal UserDetails userDetails) {

        RespuestaResult result = respuestaUseCase.marcarComoSolucion(
                respuestaId,
                userDetails.getUsername()
        );

        return ResponseEntity.ok(
                ApiResponse.ok("Respuesta marcada como solución ✓", toResponse(result)));
    }

    @DeleteMapping("/{respuestaId}")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @PathVariable Long topicoId,
            @PathVariable Long respuestaId,
            @AuthenticationPrincipal UserDetails userDetails) {

        respuestaUseCase.eliminar(respuestaId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.ok("Respuesta eliminada", null));
    }

    private RespuestaResponse toResponse(RespuestaResult result) {
        return RespuestaResponse.builder()
                .id(result.getId())
                .content(result.getContent())
                .authorUsername(result.getAuthorUsername())
                .topicoId(result.getTopicoId())
                .isSolution(result.getIsSolution())
                .totalVotos(result.getTotalVotos())
                .createdAt(result.getCreatedAt())
                .build();
    }
}