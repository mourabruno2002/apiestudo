package com.example.apiestudo.exception.domain.user;

import com.example.apiestudo.exception.base.BadRequestException;

public class WeakPasswordException extends BadRequestException {
    public WeakPasswordException(String message) {
        super(message);
    }
}
