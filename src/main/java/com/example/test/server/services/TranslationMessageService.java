package com.example.test.server.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;
import com.example.test.client.services.YaTranslationService;
import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.mappers.IncomingMessageMapper;
import com.example.test.server.mappers.TranslatedMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslationMessageService {
    private final YaTranslationService yaTranslationService;
    private final StorageService storageService;

    public TranslationMessageService(@Autowired YaTranslationService yaTranslationService,
                                     @Autowired StorageService storageService) {
        this.yaTranslationService = yaTranslationService;
        this.storageService = storageService;
    }

    public OutgoingMessageDTO sendTranslationRequest(IncomingMessageDTO incomingMessage, String requestIpAddress) {
        IncomingMessageMapper incomingMessageMapper = new IncomingMessageMapper();
        SentDTO sentDTO = incomingMessageMapper.transformToSent(incomingMessage);
        TranslatedMessageDTO translatedMessageDTO = yaTranslationService.sentTranslationRequest(sentDTO);
        storageService.saveIntoDB(incomingMessage, translatedMessageDTO, sentDTO, requestIpAddress);
        TranslatedMessageMapper translatedMessageMapper = new TranslatedMessageMapper();
        return translatedMessageMapper.transformToOutgoing(translatedMessageDTO);
    }
}
