package com.example.apiestudo.exception.base;

public class BadRequestException extends ApiException {
    public BadRequestException(String message) {
        super(message);
    }
}
