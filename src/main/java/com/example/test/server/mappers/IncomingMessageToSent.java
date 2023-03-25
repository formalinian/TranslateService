package com.example.test.server.mappers;

import com.example.test.client.dto.SentDTO;
import com.example.test.server.dto.IncomingMessageDTO;

import java.util.List;

public class IncomingMessageToSent {
    public SentDTO transformToSent(IncomingMessageDTO incomingMessage) {
        SentDTO sentDTO = new SentDTO();
        sentDTO.setTexts(List.of(incomingMessage.getText().replaceAll("\\s+", "").split("\\p{IsPunctuation}+")));
        sentDTO.setSourceLanguageCode(incomingMessage.getSourceLanguageCode());
        sentDTO.setTargetLanguageCode(incomingMessage.getTargetLanguageCode());
        return  sentDTO;
    }
}
