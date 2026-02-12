package com.example.apiestudo.exception.domain.user;

import com.example.apiestudo.exception.base.BadRequestException;

public class InvalidCurrentPasswordException extends BadRequestException {
    public InvalidCurrentPasswordException(String message) {
        super(message);
    }
}
