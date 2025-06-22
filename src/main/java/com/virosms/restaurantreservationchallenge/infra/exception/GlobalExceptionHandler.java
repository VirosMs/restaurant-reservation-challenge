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

/**
 * Global exception handler for the application.
 * This class handles various exceptions and returns standardized error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorAttributes errorAttributes;

    /**
     * Constructor for GlobalExceptionHandler.
     *
     * @param errorAttributes the ErrorAttributes to use for error handling
     */
    public GlobalExceptionHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    // --- Controladores para excepciones personalizadas ---

    /**
     * Maneja excepciones de recurso no encontrado.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.NOT_FOUND, "Recurso no encontrado", ex.getMessage(), request);
    }

    /**
     * Maneja excepciones de solicitud inválida.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.BAD_REQUEST, "Solicitud inválida", ex.getMessage(), request);
    }

    /**
     * Maneja excepciones de estado ilegal.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.CONFLICT, "Conflicto de estado", ex.getMessage(), request);
    }

    /**
     * Maneja excepciones de autorización no autorizada.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.UNAUTHORIZED, "No autorizado", ex.getMessage(), request);
    }

    /**
     * Maneja excepciones de acceso denegado.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbidden(ForbiddenException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.FORBIDDEN, "Acceso denegado", ex.getMessage(), request);
    }

    /**
     * Maneja excepciones generales.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", ex.getMessage(), request);
    }

    /**
     * Maneja excepciones de recurso no existente.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<?> handleNotExist(NotExistException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.NOT_FOUND, "No existe", ex.getMessage(), request);
    }

    /**
     * Maneja excepciones de recurso ya existente.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExists(AlreadyExistsException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.CONFLICT, "Ya existe", ex.getMessage(), request);
    }

    /**
     * Maneja excepciones de valor inválido en la solicitud.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(InvalidValueRequestException.class)
    public ResponseEntity<?> handleInvalidValueRequest(InvalidValueRequestException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.BAD_REQUEST, "Valores de entrada inválido", ex.getMessage(), request);
    }

    /**
     * Maneja excepciones de tabla no encontrada.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(NotFoundTableException.class)
    public ResponseEntity<?> handleNotFoundTable(NotFoundTableException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.NOT_FOUND, "Mesa no encontrada", ex.getMessage(), request);
    }

    /**
     * Maneja excepciones de reserva no encontrada.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(NotFoundReservation.class)
    public ResponseEntity<?> handleNotFoundReservation(NotFoundReservation ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.NOT_FOUND, "Reserva no encontrada", ex.getMessage(), request);
    }


    /**
     * Maneja excepciones de mesa inactiva.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(InactiveTableException.class)
    public ResponseEntity<?> handleInactiveTable(InactiveTableException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.BAD_REQUEST, "Mesa inactiva", ex.getMessage(), request);
    }

    // --- Manejador de errores global (404, 405, etc.) ---

    /**
     * Maneja errores globales de la aplicación.
     *
     * @param request la solicitud HTTP
     * @return una respuesta con los detalles del error
     */
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

    /**
     * Construye una respuesta de error estandarizada.
     *
     * @param status  el estado HTTP
     * @param title   el título del error
     * @param detail  los detalles del error
     * @param request la solicitud web
     * @return una ResponseEntity con el cuerpo del error y el estado HTTP
     */
    private ResponseEntity<Map<String, Object>> buildProblemResponse(
            HttpStatus status,
            String title,
            String detail,
            WebRequest request
    ) {
        String instance = request.getDescription(false).replace("uri=", "");
        return buildProblemResponse(status, title, detail, instance);
    }

    /**
     * NoHandlerFoundException es lanzada cuando no se encuentra un manejador para la ruta solicitada.
     * @param ex   excepción lanzada
     * @param request solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFound(NoHandlerFoundException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.NOT_FOUND, "Recurso no encontrado", "La ruta solicitada no existe: " + ex.getRequestURL(), request);
    }

    /**
     * Maneja excepciones de autenticación.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.UNAUTHORIZED, "No autorizado", ex.getMessage(), request);
    }

    /**
     * Maneja excepciones de acceso denegado.
     *
     * @param ex      la excepción lanzada
     * @param request la solicitud web
     * @return una respuesta con el error formateado
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return buildProblemResponse(HttpStatus.FORBIDDEN, "Acceso denegado", ex.getMessage(), request);
    }

    /**
     * Construye una respuesta de error estandarizada.
     *
     * @param status   el estado HTTP
     * @param title    el título del error
     * @param detail   los detalles del error
     * @param instance la instancia del error (ruta)
     * @return una ResponseEntity con el cuerpo del error y el estado HTTP
     */
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
