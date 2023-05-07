package com.example.test.server.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;
import com.example.test.client.dto.TranslatedWordDTO;
import com.example.test.client.services.TranslationClient;
import com.example.test.server.dto.ExceptionDTO;
import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.exceptions.ClientException;
import com.example.test.server.mappers.OutgoingMessageMapper;
import com.example.test.server.mappers.SentDtoMapper;
import com.example.test.server.mappers.SplitSentDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@Component("Multithreaded")
@RequiredArgsConstructor
public class MultithreadedTranslationService implements TranslationService {
    private final TranslationClient<TranslatedMessageDTO, SentDTO> translationClient;
    private final StorageService storageService;
    private final SentDtoMapper sentDtoMapper;
    private final OutgoingMessageMapper outgoingMessageMapper;
    private final SplitSentDtoMapper splitSentDtoMapper;

    private int getThreadID(String name) {
        String[] parts = name.split("-");
        String id = parts[parts.length - 1].trim();
        return Integer.parseInt(id);
    }

    private TranslatedMessageDTO sendMultithreadedRequest(SentDTO sentDTO) {
        List<SentDTO> sentDTOS = splitSentDtoMapper.split(sentDTO);
        ExecutorService executor = Executors.newFixedThreadPool(sentDTOS.size());
        Callable<TranslatedMessageDTO> c = ()
                -> translationClient.sendTranslationRequest(sentDTOS.get(getThreadID(Thread.currentThread().getName()) - 1));
        List<Future<TranslatedMessageDTO>> futureList = new ArrayList<>();
        for (int i = 0; i < sentDTOS.size(); i++) {
            futureList.add(executor.submit(c));
        }
        executor.shutdown();
        List<TranslatedWordDTO> translatedWords = new ArrayList<>();
        try {
            if (executor.awaitTermination(20, TimeUnit.SECONDS)) {
                for (Future<TranslatedMessageDTO> future : futureList) {
                    try {
                        translatedWords.addAll(future.get().getTranslatedWordDTOS());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (ExecutionException e) {
                        if (e.getCause() instanceof HttpClientErrorException) {
                            throw (HttpClientErrorException) e.getCause();
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        TranslatedMessageDTO translatedMessageDTO = new TranslatedMessageDTO();
        translatedMessageDTO.setTranslatedWordDTOS(translatedWords);
        return translatedMessageDTO;
    }

    public OutgoingMessageDTO translate(IncomingMessageDTO incomingMessage, String requestIpAddress) throws SQLException {
        SentDTO sentDTO = sentDtoMapper.transformToSent(incomingMessage);
        TranslatedMessageDTO translatedMessageDTO = new TranslatedMessageDTO();
        try {
            translatedMessageDTO = sendMultithreadedRequest(sentDTO);
            return outgoingMessageMapper.transformToOutgoing(translatedMessageDTO);
        } catch (HttpClientErrorException e) {
            ExceptionDTO responseBodyAs = e.getResponseBodyAs(ExceptionDTO.class);
            if (responseBodyAs != null) {
                throw new ClientException(responseBodyAs.getMessage(), HttpStatus.BAD_REQUEST.value());
            } else throw new ClientException(e.getMessage(), e.getStatusCode().value());
        } finally {
            storageService.save(incomingMessage, outgoingMessageMapper.transformToOutgoing(translatedMessageDTO), sentDTO, requestIpAddress);
        }
    }
}
