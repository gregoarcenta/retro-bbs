package com.retrobbs.backend.infrastructure.adapters.in.rest;

import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ranking")
class RankingController {

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getRanking() {
        return ResponseEntity.ok(ApiResponse.ok("Ranking", null));
    }
}
