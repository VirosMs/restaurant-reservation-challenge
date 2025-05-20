package com.virosms.restaurantreservationchallenge.infra.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorAttributes errorAttributes;

    public GlobalExceptionHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    // --- Controladores para excepciones personalizadas ---

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.NOT_FOUND, "Recurso no encontrado", ex.getMessage(), request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.BAD_REQUEST, "Solicitud inválida", ex.getMessage(), request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.CONFLICT, "Conflicto de estado", ex.getMessage(), request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.UNAUTHORIZED, "No autorizado", ex.getMessage(), request);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbidden(ForbiddenException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.FORBIDDEN, "Acceso denegado", ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", ex.getMessage(), request);
    }

    // --- Manejador de errores global (404, 405, etc.) ---

    @RequestMapping("/error")
    public ResponseEntity<?> handleError(HttpServletRequest request) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(
                webRequest,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE)
        );

        int status = (int) errorDetails.getOrDefault("status", 500);
        String path = (String) errorDetails.getOrDefault("path", request.getRequestURI());
        String message = (String) errorDetails.getOrDefault("message", "Error desconocido");

        HttpStatus httpStatus = HttpStatus.valueOf(status);
        return buildProblemResponse(httpStatus, httpStatus.getReasonPhrase(), message, path);
    }

    // --- Constructor de respuestas estándar ---

    private ResponseEntity<Map<String, Object>> buildProblemResponse(
            HttpStatus status,
            String title,
            String detail,
            WebRequest request
    ) {
        String instance = request.getDescription(false).replace("uri=", "");
        return buildProblemResponse(status, title, detail, instance);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFound(NoHandlerFoundException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.NOT_FOUND, "Recurso no encontrado", "La ruta solicitada no existe: " + ex.getRequestURL(), request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.UNAUTHORIZED, "No autorizado", ex.getMessage(), request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.FORBIDDEN, "Acceso denegado", ex.getMessage(), request);
    }

    private ResponseEntity<Map<String, Object>> buildProblemResponse(
            HttpStatus status,
            String title,
            String detail,
            String instance
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("type", "https://virosms.com/errors/" + status.name().toLowerCase());
        body.put("title", title);
        body.put("status", status.value());
        body.put("detail", detail);
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("instance", instance);
        return new ResponseEntity<>(body, status);
    }
}
