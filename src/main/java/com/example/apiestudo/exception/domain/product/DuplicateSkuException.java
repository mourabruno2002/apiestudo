package com.example.apiestudo.exception.domain.product;

import com.example.apiestudo.exception.base.ConflictException;

public class DuplicateSkuException extends ConflictException {
    public DuplicateSkuException(String message) {
        super(message);
    }
}
