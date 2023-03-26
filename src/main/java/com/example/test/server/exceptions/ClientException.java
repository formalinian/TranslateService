package com.example.test.server.exceptions;

public class ClientException extends RuntimeException {
    private final String message;
    public ClientException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
