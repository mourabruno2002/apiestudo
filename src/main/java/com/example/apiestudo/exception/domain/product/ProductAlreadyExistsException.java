package com.example.apiestudo.exception.domain.product;

import com.example.apiestudo.exception.base.ConflictException;

public class ProductAlreadyExistsException extends ConflictException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
