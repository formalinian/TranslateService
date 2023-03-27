package com.example.test.server.exceptions;

public class CustomException extends RuntimeException {
    private final int code;
    private final String message;

    public CustomException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
