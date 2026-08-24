package com.valtiel.forgeOps.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record CreateUserDTO(

        @NotBlank(message = "El nombre de usuario es obligatorio")
        String username,
        @NotBlank(message = "La contraseña es obligatoria")
        String password,
        @Email(message = "Ingresa un email válido")
        @NotBlank(message = "El email es obligatorio")
        String email,
        @NotEmpty(message = "Debes ingresar al menos un rol")
        Set<Long> roles_id

) {}