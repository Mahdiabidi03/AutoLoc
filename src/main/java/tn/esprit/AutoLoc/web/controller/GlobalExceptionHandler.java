package tn.esprit.AutoLoc.web.controller;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tn.esprit.AutoLoc.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErreurApi(int statut, String message, LocalDateTime horodatage) {
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErreurApi> handleNotFound(ResourceNotFoundException e) {
        return construire(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErreurApi> handleBadRequest(IllegalArgumentException e) {
        return construire(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurApi> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(erreur -> erreur.getField() + " : " + erreur.getDefaultMessage())
                .collect(Collectors.joining(" ; "));
        return construire(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<ErreurApi> construire(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ErreurApi(status.value(), message, LocalDateTime.now()));
    }
}