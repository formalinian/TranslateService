package com.example.test.server.entities;

public class RequestWordEntity {
    private Long id;
    private Long idRequest;
    private String incomingWord;
    private String translatedWord;

    public RequestWordEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Long idRequest) {
        this.idRequest = idRequest;
    }

    public String getIncomingWord() {
        return incomingWord;
    }

    public void setIncomingWord(String incomingWord) {
        this.incomingWord = incomingWord;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public void setTranslatedWord(String translatedWord) {
        this.translatedWord = translatedWord;
    }
}