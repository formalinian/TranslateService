package com.example.test.client.dtos;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class TranslatedMessageDTO {
    @JsonSetter("translations")
    private List<TranslatedWordDTO> translatedWordDTOS;

    public TranslatedMessageDTO() {
    }

    public List<TranslatedWordDTO> getTranslatedWordDTOS() {
        return translatedWordDTOS;
    }

    public void setTranslatedWordDTOS(List<TranslatedWordDTO> translatedWordDTOS) {
        this.translatedWordDTOS = translatedWordDTOS;
    }

}
