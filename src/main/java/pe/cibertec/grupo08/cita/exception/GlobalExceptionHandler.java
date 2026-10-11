package pe.cibertec.grupo08.cita.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.cibertec.grupo08.cita.dto.ErrorResponse;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex, HttpServletRequest request) {
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), Map.of());
    }

    @ExceptionHandler(ConflictoCitaException.class)
    public ResponseEntity<ErrorResponse> manejarConflicto(ConflictoCitaException ex, HttpServletRequest request) {
        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI(), Map.of());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> manejarArgumentoInvalido(IllegalArgumentException ex, HttpServletRequest request) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI(), Map.of());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> manejarEstadoInvalido(IllegalStateException ex, HttpServletRequest request) {
        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI(), Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.putIfAbsent(error.getField(), error.getDefaultMessage())
        );
        return construirRespuesta(HttpStatus.BAD_REQUEST, "La solicitud contiene datos inválidos", request.getRequestURI(), errores);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> manejarRestricciones(ConstraintViolationException ex, HttpServletRequest request) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, "Uno o más parámetros no son válidos",request.getRequestURI(), Map.of());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, "El JSON es inválido o contiene valores no reconocidos", request.getRequestURI(), Map.of());
    }

    private ResponseEntity<ErrorResponse> construirRespuesta(HttpStatus status, String mensaje, String ruta, Map<String, String> errores) {
        ErrorResponse respuesta = new ErrorResponse(LocalDateTime.now(),status.value(),status.getReasonPhrase(),mensaje,ruta, errores);
        return ResponseEntity.status(status).body(respuesta);
    }
}