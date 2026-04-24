package com.retrobbs.backend.infrastructure.adapters.in.rest.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 20, message = "El username debe tener entre 3 y 20 caracteres")
    @Pattern(
            regexp = "^[a-zA-Z0-9_-]+$",
            message = "El username solo puede tener letras, números, guiones y guiones bajos"
    )
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "Email demasiado largo")
    private String email;

    @NotBlank(message = "El password es obligatorio")
    @Size(min = 8, max = 60, message = "El password debe tener entre 8 y 60 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "El password debe tener al menos una mayúscula, una minúscula y un número"
    )
    private String password;

    @Size(max = 300, message = "La bio no puede superar los 300 caracteres")
    private String bio;
}