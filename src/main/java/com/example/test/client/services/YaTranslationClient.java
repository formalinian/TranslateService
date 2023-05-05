package com.example.test.client.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@ConditionalOnProperty(value = "translation.multithreading", havingValue = "false")
@RequiredArgsConstructor
@Getter
public class YaTranslationClient implements TranslationClient<TranslatedMessageDTO, SentDTO> {
    private final RestTemplate restTemplate;
    @Value("${yandex.token}")
    private String yandexToken;

    @Value("${yandex.translate}")
    private String yandexURL;

    public TranslatedMessageDTO sentTranslationRequest(SentDTO sentDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", yandexToken);
        HttpEntity<SentDTO> httpEntity = new HttpEntity<>(sentDTO, headers);
        return restTemplate.postForObject(yandexURL, httpEntity, TranslatedMessageDTO.class);
    }
}
