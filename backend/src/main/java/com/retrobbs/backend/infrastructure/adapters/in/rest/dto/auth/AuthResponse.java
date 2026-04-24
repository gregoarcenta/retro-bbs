package com.retrobbs.backend.infrastructure.adapters.in.rest.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private final String token;
    private final String username;
    private final String email;
    private final String role;
    private final Integer level;
    private final Integer points;
}