package com.retrobbs.backend.infrastructure.adapters.in.rest;

import com.retrobbs.backend.domain.exception.DuplicateResourceException;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.auth.AuthResponse;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.auth.LoginRequest;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.auth.RegisterRequest;
import com.retrobbs.backend.infrastructure.adapters.in.rest.dto.common.ApiResponse;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.PerfilEntity;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.entity.UsuarioEntity;
import com.retrobbs.backend.infrastructure.adapters.out.jpa.repository.UsuarioJpaRepository;
import com.retrobbs.backend.infrastructure.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioJpaRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("username", request.getUsername());
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("email", request.getEmail());
        }

        UsuarioEntity usuario = UsuarioEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        PerfilEntity perfil = PerfilEntity.builder()
                .usuario(usuario)
                .bio(request.getBio())
                .points(0)
                .level(1)
                .build();

        usuario.setPerfil(perfil);
        usuarioRepository.save(usuario);

        AuthResponse authResponse = buildAuthResponse(usuario, perfil);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Usuario registrado correctamente", authResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        UsuarioEntity usuario = usuarioRepository
                .findByUsername(request.getUsername())
                .orElseThrow();

        AuthResponse authResponse = buildAuthResponse(usuario, usuario.getPerfil());

        return ResponseEntity.ok(
                ApiResponse.ok("Sesión iniciada correctamente", authResponse)
        );
    }

    private AuthResponse buildAuthResponse(UsuarioEntity usuario, PerfilEntity perfil) {
        return AuthResponse.builder()
                .token(jwtTokenProvider.generateToken(usuario.getUsername(), usuario.getRole().name()))
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .role(usuario.getRole().name())
                .level(perfil != null ? perfil.getLevel() : 1)
                .points(perfil != null ? perfil.getPoints() : 0)
                .build();
    }
}