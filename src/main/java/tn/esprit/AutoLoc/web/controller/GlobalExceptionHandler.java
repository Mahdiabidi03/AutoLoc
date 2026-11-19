package tn.esprit.AutoLoc.web.controller;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private ResponseEntity<ErreurApi> construire(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ErreurApi(status.value(), message, LocalDateTime.now()));
    }
}