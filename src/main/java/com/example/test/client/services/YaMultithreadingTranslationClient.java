package com.example.test.client.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;
import com.example.test.client.dto.TranslatedWordDTO;
import com.example.test.server.mappers.SplitSentDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


@Service
@ConditionalOnProperty(value = "translation.multithreading", havingValue = "true")
@RequiredArgsConstructor
public class YaMultithreadingTranslationClient implements TranslationClient<TranslatedMessageDTO, SentDTO> {
    private final RestTemplate restTemplate;
    private final SplitSentDtoMapper splitSentDtoMapper;
    @Value("${yandex.token}")
    private String yandexToken;

    @Value("${yandex.translate}")
    private String yandexURL;

    private int getThreadID(String name) {
        String[] parts = name.split("-");
        String id = parts[parts.length - 1].trim();
        return Integer.parseInt(id);
    }

    private TranslatedMessageDTO sentRequest(SentDTO sentDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", yandexToken);
        HttpEntity<SentDTO> httpEntity = new HttpEntity<>(sentDTO, headers);
        return restTemplate.postForObject(yandexURL, httpEntity, TranslatedMessageDTO.class);
    }

    public TranslatedMessageDTO sentTranslationRequest(SentDTO sentDTO) {
        List<SentDTO> sentDTOS = splitSentDtoMapper.split(sentDTO);
        ExecutorService executor = Executors.newFixedThreadPool(sentDTOS.size());
        Callable<TranslatedMessageDTO> c = ()
                -> sentRequest(sentDTOS.get(getThreadID(Thread.currentThread().getName()) - 1));
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
                        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
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
}
