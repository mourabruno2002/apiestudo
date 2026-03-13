package com.example.apiestudo.exception.domain.order;

public class InvalidShippingException extends RuntimeException {
    public InvalidShippingException(String message) {
        super(message);
    }
}
