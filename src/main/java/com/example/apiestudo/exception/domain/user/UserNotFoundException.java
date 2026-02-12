package com.example.apiestudo.exception.domain.user;


import com.example.apiestudo.exception.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
