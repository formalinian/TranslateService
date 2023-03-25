package com.example.test.server.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;
import com.example.test.client.dto.TranslatedWordDTO;
import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.entities.RequestMessageEntity;
import com.example.test.server.entities.RequestWordEntity;
import com.example.test.server.repositories.RequestMessageRepo;
import com.example.test.server.repositories.RequestWordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageService {
    private final RequestMessageRepo requestMessageRepo;
    private final RequestWordRepo requestWordRepo;

    public StorageService(@Autowired RequestMessageRepo requestMessageRepo,
                          @Autowired RequestWordRepo requestWordRepo) {
        this.requestMessageRepo = requestMessageRepo;
        this.requestWordRepo = requestWordRepo;
    }

    public void saveIntoDB(IncomingMessageDTO incomingMessageDTO,
                           TranslatedMessageDTO translatedMessageDTO,
                           SentDTO sentDTO,
                           String ipAddress) {
        RequestMessageEntity requestMessageEntity = new RequestMessageEntity();
        List<TranslatedWordDTO> wordDTOS = translatedMessageDTO.getTranslatedWordDTOS();
        //TODO сделать маппер
        requestMessageEntity.setIncomingMessage(incomingMessageDTO.getText());
        requestMessageEntity.setTranslatedMessage(wordDTOS.stream().map(TranslatedWordDTO::getText).collect(Collectors.joining(" ")));
        requestMessageEntity.setSourceLanguage(incomingMessageDTO.getSourceLanguageCode());
        requestMessageEntity.setTargetLanguage(incomingMessageDTO.getTargetLanguageCode());
        requestMessageEntity.setRequestTime(LocalDateTime.now().toString());
        requestMessageEntity.setRequestIp(ipAddress);
        requestMessageRepo.saveMessage(requestMessageEntity);
        List<String> words = wordDTOS.stream().map(TranslatedWordDTO::getText).toList();
        for (int i = 0; i < words.size(); i++) {
            RequestWordEntity requestWordEntity = new RequestWordEntity();
            //TODO тут тоже сделать маппер
            requestWordEntity.setId((long)i+1);
            requestWordEntity.setIdRequest(requestMessageEntity.getId());
            requestWordEntity.setIncomingWord(sentDTO.getTexts().get(i));
            requestWordEntity.setTranslatedWord(words.get(i));
            requestWordRepo.saveWord(requestWordEntity);
        }
    }
}
