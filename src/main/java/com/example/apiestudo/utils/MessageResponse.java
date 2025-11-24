package com.example.apiestudo.utils;

public class MessageResponse<T> {

    private String message;
    private T data;

    public MessageResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public MessageResponse(String message) {
        this.message = message;
    }
}
