package com.example.test.server.mappers;

import com.example.test.client.dto.TranslatedMessageDTO;
import com.example.test.client.dto.TranslatedWordDTO;
import com.example.test.server.dto.OutgoingMessageDTO;

import java.util.List;
import java.util.stream.Collectors;

public class OutgoingMessageMapper {
    public OutgoingMessageDTO transformToOutgoing(TranslatedMessageDTO translatedMessageDTO) {
        OutgoingMessageDTO outgoingMessage = new OutgoingMessageDTO();
        List<TranslatedWordDTO> list = translatedMessageDTO.getTranslatedWordDTOS();
        outgoingMessage.setText(list.stream().map(TranslatedWordDTO::getText).collect(Collectors.joining(" ")));
        return  outgoingMessage;
    }
}
