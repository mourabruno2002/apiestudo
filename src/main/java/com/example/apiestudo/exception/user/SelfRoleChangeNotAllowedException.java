package com.example.apiestudo.exception.user;

public class SelfRoleChangeNotAllowedException extends RuntimeException {
    public SelfRoleChangeNotAllowedException(String message) {
        super(message);
    }
}
