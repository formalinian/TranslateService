package com.example.test.client.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;
import com.example.test.server.dto.YaExceptionDTO;
import com.example.test.server.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
public class YaTranslationService {
    private final RestTemplate restTemplate;
    @Value("${yandex.token}")
    private String yandexToken;

    @Value("${yandex.translate}")
    private String yandexURL;

    public YaTranslationService(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TranslatedMessageDTO sentTranslationRequest(SentDTO sentDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", yandexToken);
        HttpEntity<SentDTO> httpEntity = new HttpEntity<>(sentDTO, headers);
        return restTemplate.postForObject(yandexURL, httpEntity, TranslatedMessageDTO.class);
    }
}
