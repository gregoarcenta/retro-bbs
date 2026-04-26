package com.retrobbs.backend.infrastructure.adapters.in.rest;

import com.retrobbs.backend.domain.model.RankingResult;
import com.retrobbs.backend.domain.ports.in.RankingUseCase;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.common.ApiResponse;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.ranking.RankingItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingUseCase rankingUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RankingItemResponse>>> top10(
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM") LocalDate mes) {

        List<RankingResult> resultados = mes != null
                ? rankingUseCase.obtenerTop10DelMes(mes)
                : rankingUseCase.obtenerTop10();

        List<RankingItemResponse> response = resultados.stream()
                .map(this::toResponse)
                .toList();

        String periodo = mes != null
                ? "Ranking de " + mes.getMonth().name() + " " + mes.getYear()
                : "Ranking del mes actual";

        return ResponseEntity.ok(ApiResponse.ok(periodo, response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Double>> misPuntos(@AuthenticationPrincipal UserDetails userDetails) {

        Double puntos = rankingUseCase.obtenerPuntosUsuario(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.ok("Tus puntos del mes actual", puntos));
    }

    private RankingItemResponse toResponse(RankingResult result) {
        return RankingItemResponse.builder()
                .posicion(result.getPosicion())
                .username(result.getUsername())
                .puntos(result.getPuntos())
                .nivel(result.getNivel())
                .build();
    }
}