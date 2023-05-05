package com.example.test.server.mappers;

import com.example.test.client.dto.SentDTO;
import com.example.test.server.dto.IncomingMessageDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SentDtoMapper {
    public SentDTO transformToSent(IncomingMessageDTO incomingMessage) {
        SentDTO sentDTO = new SentDTO();
        List<String> texts = new java.util.ArrayList<>(List.of(incomingMessage.getText().split(" ")));
        texts.removeIf(String::isEmpty);
        sentDTO.setTexts(texts);
        sentDTO.setSourceLanguageCode(incomingMessage.getSourceLanguageCode());
        sentDTO.setTargetLanguageCode(incomingMessage.getTargetLanguageCode());
        return sentDTO;
    }
}
