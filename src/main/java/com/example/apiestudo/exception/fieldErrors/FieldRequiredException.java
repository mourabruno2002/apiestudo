package com.example.apiestudo.exception.fieldErrors;

public class FieldRequiredException extends RuntimeException {
    public FieldRequiredException(String message) {
        super(message);
    }
}
