package com.example.test.server.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;
import com.example.test.client.services.YaTranslationService;
import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.dto.ExceptionDTO;
import com.example.test.server.exceptions.CustomException;
import com.example.test.server.mappers.SentDtoMapper;
import com.example.test.server.mappers.OutgoingMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class TranslationRequestService {
    private final YaTranslationService yaTranslationService;
    private final StorageService storageService;

    public TranslationRequestService(@Autowired YaTranslationService yaTranslationService,
                                     @Autowired StorageService storageService) {
        this.yaTranslationService = yaTranslationService;
        this.storageService = storageService;
    }

    public OutgoingMessageDTO sendTranslationRequest(IncomingMessageDTO incomingMessage, String requestIpAddress) {
        SentDtoMapper sentDtoMapper = new SentDtoMapper();
        SentDTO sentDTO = sentDtoMapper.transformToSent(incomingMessage);
        TranslatedMessageDTO translatedMessageDTO = new TranslatedMessageDTO();
        OutgoingMessageMapper outgoingMessageMapper = new OutgoingMessageMapper();
        try {
            translatedMessageDTO = yaTranslationService.sentTranslationRequest(sentDTO);
            return outgoingMessageMapper.transformToOutgoing(translatedMessageDTO);
        } catch (HttpClientErrorException e) {
            ExceptionDTO responseBodyAs = e.getResponseBodyAs(ExceptionDTO.class);
            throw new CustomException(responseBodyAs.getMessage(), responseBodyAs.getCode());
        } finally {
            storageService.saveIntoDB(incomingMessage, outgoingMessageMapper.transformToOutgoing(translatedMessageDTO), sentDTO, requestIpAddress);
        }
    }
}
