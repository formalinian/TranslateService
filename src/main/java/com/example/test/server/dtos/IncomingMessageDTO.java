package com.example.test.server.dtos;

public class IncomingMessageDTO {
    private String text;
    private String sourceLanguageCode;
    private String targetLanguageCode;

    public IncomingMessageDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSourceLanguageCode() {
        return sourceLanguageCode;
    }

    public void setSourceLanguageCode(String sourceLanguageCode) {
        this.sourceLanguageCode = sourceLanguageCode;
    }

    public String getTargetLanguageCode() {
        return targetLanguageCode;
    }

    public void setTargetLanguageCode(String targetLanguageCode) {
        this.targetLanguageCode = targetLanguageCode;
    }
}
