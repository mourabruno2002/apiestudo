package com.example.apiestudo.exception.domain.product;

import com.example.apiestudo.exception.base.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
