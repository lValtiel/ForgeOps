package com.valtiel.forgeOps.security;

import com.valtiel.forgeOps.dto.errorDTO.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHanlder implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpServletResponse.SC_FORBIDDEN,
                "Acceso denegado",
                "No tienes permisos para acceder a este recurso",
                LocalDateTime.now(),
                request.getRequestURI()
        );

        objectMapper.writeValue(response.getOutputStream(), errorResponseDTO);
    }
}
