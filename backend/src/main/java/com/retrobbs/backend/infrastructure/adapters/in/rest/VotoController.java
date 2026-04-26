package com.retrobbs.backend.infrastructure.adapters.in.rest;

import com.retrobbs.backend.domain.ports.in.VotoUseCase;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.common.ApiResponse;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.voto.VotoRequest;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.voto.VotoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votos")
@RequiredArgsConstructor
public class VotoController {

    private final VotoUseCase votoUseCase;

    @PostMapping("/{targetId}")
    public ResponseEntity<ApiResponse<VotoResponse>> votar(
            @PathVariable Long targetId,
            @Valid @RequestBody VotoRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        int nuevoTotal = votoUseCase.votar(
                targetId,
                request.getTipo(),
                userDetails.getUsername(),
                request.getTargetType()
        );

        return ResponseEntity.ok(ApiResponse.ok(
                "Voto registrado",
                VotoResponse.builder()
                        .mensaje("Voto registrado correctamente")
                        .nuevoTotal(nuevoTotal)
                        .targetId(targetId)
                        .targetType(request.getTargetType())
                        .build()
        ));
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<ApiResponse<VotoResponse>> quitarVoto(
            @PathVariable Long targetId,
            @RequestParam String targetType,
            @AuthenticationPrincipal UserDetails userDetails) {

        int nuevoTotal = votoUseCase.quitarVoto(
                targetId,
                userDetails.getUsername(),
                targetType
        );

        return ResponseEntity.ok(ApiResponse.ok(
                "Voto eliminado",
                VotoResponse.builder()
                        .mensaje("Voto eliminado correctamente")
                        .nuevoTotal(nuevoTotal)
                        .targetId(targetId)
                        .targetType(targetType)
                        .build()
        ));
    }
}