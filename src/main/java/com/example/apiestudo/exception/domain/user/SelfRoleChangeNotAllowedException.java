package com.example.apiestudo.exception.domain.user;

import com.example.apiestudo.exception.base.BadRequestException;

public class SelfRoleChangeNotAllowedException extends BadRequestException {
    public SelfRoleChangeNotAllowedException(String message) {
        super(message);
    }
}
