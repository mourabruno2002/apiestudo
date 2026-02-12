package com.example.apiestudo.exception.domain.user;

import com.example.apiestudo.exception.base.ConflictException;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
