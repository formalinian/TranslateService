package com.example.test.server.dtos;

public class OutgoingMessageDTO {
    private String text;

    public OutgoingMessageDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
