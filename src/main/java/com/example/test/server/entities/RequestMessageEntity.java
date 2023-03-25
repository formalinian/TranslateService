package com.example.test.server.entities;

public class RequestMessageEntity {
    private int id;
    private String incomingMessage;
    private String translatedMessage;
    private String sourceLanguage;
    private String targetLanguage;
    private String requestTime;
    private String requestIp;

    public RequestMessageEntity(int id,
                                String incomingMessage,
                                String translatedMessage,
                                String sourceLanguage,
                                String targetLanguage,
                                String requestTime,
                                String requestIp) {
        this.id = id;
        this.incomingMessage = incomingMessage;
        this.translatedMessage = translatedMessage;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.requestTime = requestTime;
        this.requestIp = requestIp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIncomingMessage() {
        return incomingMessage;
    }

    public void setIncomingMessage(String incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    public String getTranslatedMessage() {
        return translatedMessage;
    }

    public void setTranslatedMessage(String translatedMessage) {
        this.translatedMessage = translatedMessage;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
}
