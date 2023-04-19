package com.example.test.server.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;
import com.example.test.client.services.TranslationClient;
import com.example.test.server.dto.ExceptionDTO;
import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.exceptions.CustomException;
import com.example.test.server.mappers.OutgoingMessageMapper;
import com.example.test.server.mappers.SentDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class TranslationRequestService {
    private final TranslationClient<TranslatedMessageDTO, SentDTO> translationClient;
    private final StorageService storageService;
    private final SentDtoMapper sentDtoMapper;
    private final OutgoingMessageMapper outgoingMessageMapper;

    public OutgoingMessageDTO sendTranslationRequest(IncomingMessageDTO incomingMessage, String requestIpAddress) {
        SentDTO sentDTO = sentDtoMapper.transformToSent(incomingMessage);
        TranslatedMessageDTO translatedMessageDTO = new TranslatedMessageDTO();
        try {
            translatedMessageDTO = translationClient.sentTranslationRequest(sentDTO);
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
