package com.forohub.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(403).body("Acceso denegado: No está autenticado");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> tratarEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> tratarNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList());
    }

    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity<?> errorHandlerValidacionesDeIntegridad(ValidacionDeIntegridad e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> errorHandlerValidacionesDeNegocio(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}

