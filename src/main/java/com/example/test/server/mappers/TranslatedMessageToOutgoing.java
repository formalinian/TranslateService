package com.example.test.server.mappers;

import com.example.test.client.dtos.TranslatedMessageDTO;
import com.example.test.client.dtos.TranslatedWordDTO;
import com.example.test.server.dtos.OutgoingMessageDTO;

import java.util.List;
import java.util.stream.Collectors;

public class TranslatedMessageToOutgoing {
    public OutgoingMessageDTO transformToOutgoing(TranslatedMessageDTO translatedMessageDTO) {
        OutgoingMessageDTO outgoingMessage = new OutgoingMessageDTO();
        List<TranslatedWordDTO> list = translatedMessageDTO.getTranslatedWordDTOS();
        outgoingMessage.setText(list.stream().map(x -> x.getText()).collect(Collectors.joining(" ")));
        return  outgoingMessage;
    }
}
