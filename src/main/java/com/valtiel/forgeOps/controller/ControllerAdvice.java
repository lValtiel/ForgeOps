package com.valtiel.forgeOps.controller;

import com.valtiel.forgeOps.dto.errorDTO.ErrorResponseDTO;
import com.valtiel.forgeOps.exception.DuplicateResourceException;
import com.valtiel.forgeOps.exception.InvalidCredentialsException;
import com.valtiel.forgeOps.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundExpcetion(ResourceNotFoundException exception, HttpServletRequest request) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                exception.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing
                ));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateResourceException(DuplicateResourceException exception, HttpServletRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                "CONFLICT_ERROR",
                exception.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException exception, HttpServletRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                "Error de autenticación",
                exception.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
