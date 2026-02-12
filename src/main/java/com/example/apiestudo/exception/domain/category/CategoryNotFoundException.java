package com.example.apiestudo.exception.domain.category;

import com.example.apiestudo.exception.base.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
