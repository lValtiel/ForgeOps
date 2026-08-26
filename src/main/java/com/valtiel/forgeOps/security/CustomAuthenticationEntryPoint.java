package com.valtiel.forgeOps.security;

import com.valtiel.forgeOps.dto.errorDTO.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String message = authException.getMessage() != null
                ? authException.getMessage()
                : "Error de autenticación. Por favor vuelve a iniciar sesión";

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Error de autenticación",
                message,
                LocalDateTime.now(),
                request.getRequestURI()
        );

        objectMapper.writeValue(response.getOutputStream(), errorResponseDTO);
    }
}
