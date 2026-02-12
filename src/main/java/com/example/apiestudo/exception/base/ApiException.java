package com.example.apiestudo.exception.base;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
