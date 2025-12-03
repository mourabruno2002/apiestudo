package com.example.apiestudo.exception.fieldErrors;

public class FieldInvalidException extends RuntimeException {
    public FieldInvalidException(String message) {
        super(message);
    }
}
