package com.example.test.server.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;
import com.example.test.client.services.TranslationClient;
import com.example.test.server.dto.ExceptionDTO;
import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.exceptions.ClientException;
import com.example.test.server.mappers.OutgoingMessageMapper;
import com.example.test.server.mappers.SentDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationClient<TranslatedMessageDTO, SentDTO> translationClient;
    private final StorageService storageService;
    private final SentDtoMapper sentDtoMapper;
    private final OutgoingMessageMapper outgoingMessageMapper;

    public OutgoingMessageDTO sendTranslationRequest(IncomingMessageDTO incomingMessage, String requestIpAddress) throws SQLException {
        SentDTO sentDTO = sentDtoMapper.transformToSent(incomingMessage);
        TranslatedMessageDTO translatedMessageDTO = new TranslatedMessageDTO();
        try {
            translatedMessageDTO = translationClient.sentTranslationRequest(sentDTO);
            return outgoingMessageMapper.transformToOutgoing(translatedMessageDTO);
        } catch (HttpClientErrorException e) {
            ExceptionDTO responseBodyAs = e.getResponseBodyAs(ExceptionDTO.class);
            if (responseBodyAs != null) {
                throw new ClientException(responseBodyAs.getMessage(), HttpStatus.BAD_REQUEST.value());
            } else throw new ClientException(e.getMessage(), e.getStatusCode().value());
        } finally {
            storageService.saveIntoDB(incomingMessage, outgoingMessageMapper.transformToOutgoing(translatedMessageDTO), sentDTO, requestIpAddress);
        }
    }
}
