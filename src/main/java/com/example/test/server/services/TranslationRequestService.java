package com.example.test.server.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;
import com.example.test.client.services.YaTranslationService;
import com.example.test.server.dto.ExceptionDTO;
import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.exceptions.CustomException;
import com.example.test.server.mappers.OutgoingMessageMapper;
import com.example.test.server.mappers.SentDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class TranslationRequestService {
    private final YaTranslationService yaTranslationService;
    private final StorageService storageService;
    private final SentDtoMapper sentDtoMapper;
    private final OutgoingMessageMapper outgoingMessageMapper;

    public TranslationRequestService(@Autowired YaTranslationService yaTranslationService,
                                     @Autowired StorageService storageService,
                                     @Autowired SentDtoMapper sentDtoMapper,
                                     @Autowired OutgoingMessageMapper outgoingMessageMapper) {
        this.yaTranslationService = yaTranslationService;
        this.storageService = storageService;
        this.sentDtoMapper = sentDtoMapper;
        this.outgoingMessageMapper = outgoingMessageMapper;
    }

    public OutgoingMessageDTO sendTranslationRequest(IncomingMessageDTO incomingMessage, String requestIpAddress) {
        SentDTO sentDTO = sentDtoMapper.transformToSent(incomingMessage);
        TranslatedMessageDTO translatedMessageDTO = new TranslatedMessageDTO();
        try {
            translatedMessageDTO = yaTranslationService.sentTranslationRequest(sentDTO);
            return outgoingMessageMapper.transformToOutgoing(translatedMessageDTO);
        } catch (HttpClientErrorException e) {
            ExceptionDTO responseBodyAs = e.getResponseBodyAs(ExceptionDTO.class);
            assert responseBodyAs != null;
            throw new CustomException(responseBodyAs.getMessage(), HttpStatus.BAD_REQUEST.value());
        } finally {
            storageService.saveIntoDB(incomingMessage, outgoingMessageMapper.transformToOutgoing(translatedMessageDTO), sentDTO, requestIpAddress);
        }
    }
}
