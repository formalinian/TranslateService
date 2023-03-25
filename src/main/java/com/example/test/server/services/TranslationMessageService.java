package com.example.test.server.services;

import com.example.test.client.dtos.SentDTO;
import com.example.test.client.dtos.TranslatedMessageDTO;
import com.example.test.client.services.YaTranslationService;
import com.example.test.server.dtos.IncomingMessageDTO;
import com.example.test.server.dtos.OutgoingMessageDTO;
import com.example.test.server.mappers.IncomingMessageToSent;
import com.example.test.server.mappers.TranslatedMessageToOutgoing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslationMessageService {
    private final YaTranslationService yaTranslationService;
    private final IncomingMessageToSent incomingMessageToSent;
    private final TranslatedMessageToOutgoing translatedMessageToOutgoing;

    public TranslationMessageService(@Autowired YaTranslationService yaTranslationService,
                                    @Autowired IncomingMessageToSent incomingMessageToSent,
                                    @Autowired TranslatedMessageToOutgoing translatedMessageToOutgoing) {
        this.yaTranslationService = yaTranslationService;
        this.incomingMessageToSent = incomingMessageToSent;
        this.translatedMessageToOutgoing = translatedMessageToOutgoing;
    }

    public OutgoingMessageDTO translate(IncomingMessageDTO incomingMessage) {
        SentDTO sentDTO = incomingMessageToSent.transformToSent(incomingMessage);
        TranslatedMessageDTO translatedMessageDTO = yaTranslationService.sentTranslationRequest(sentDTO);
        return translatedMessageToOutgoing.transformToOutgoing(translatedMessageDTO);
    }
}
