package com.example.demo.exception;

/**
 * Thrown when an operation conflicts with database rules
 * (e.g. the trg_prevent_user_delete trigger blocking user deletion).
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
