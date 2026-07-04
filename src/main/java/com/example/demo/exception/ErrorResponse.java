package com.example.demo.exception;

import java.time.LocalDateTime;

/**
 * Standard JSON error body returned by the global exception handler.
 */
public record ErrorResponse(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {
    public static ErrorResponse of(int status, String error, String message) {
        return new ErrorResponse(status, error, message, LocalDateTime.now());
    }
}
