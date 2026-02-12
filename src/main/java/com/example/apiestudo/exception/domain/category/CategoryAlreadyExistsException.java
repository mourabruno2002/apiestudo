package com.example.apiestudo.exception.domain.category;

import com.example.apiestudo.exception.base.ConflictException;

public class CategoryAlreadyExistsException extends ConflictException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
