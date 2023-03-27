package com.example.test.server.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;
import com.example.test.client.dto.TranslatedWordDTO;
import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.entities.RequestDataEntity;
import com.example.test.server.entities.RequestWordEntity;
import com.example.test.server.mappers.OutgoingMessageMapper;
import com.example.test.server.mappers.RequestDataMapper;
import com.example.test.server.mappers.RequestWordMapper;
import com.example.test.server.repositories.RequestDataRepo;
import com.example.test.server.repositories.RequestWordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageService {
    private final RequestDataRepo requestDataRepo;
    private final RequestWordRepo requestWordRepo;

    public StorageService(@Autowired RequestDataRepo requestDataRepo,
                          @Autowired RequestWordRepo requestWordRepo) {
        this.requestDataRepo = requestDataRepo;
        this.requestWordRepo = requestWordRepo;
    }

    public void saveIntoDB(IncomingMessageDTO incomingMessageDTO,
                           OutgoingMessageDTO outgoingMessageDTO,
                           SentDTO sentDTO,
                           String ipAddress) {
        RequestDataMapper requestDataMapper = new RequestDataMapper();
        RequestDataEntity requestDataEntity = requestDataMapper.transformToReqDataEntity(incomingMessageDTO, outgoingMessageDTO, ipAddress);
        requestDataRepo.saveMessage(requestDataEntity);
        List<String> words = new ArrayList<>();
        if (outgoingMessageDTO.getText() != null) {
             words = Arrays.stream(outgoingMessageDTO.getText().split(" ")).toList();
        }
        for (int i = 0; i < words.size(); i++) {
            RequestWordMapper requestWordMapper = new RequestWordMapper();
            requestWordRepo.saveWord(requestWordMapper.transformToReqWordEntity(
                    (long)i+1,
                    requestDataEntity.getId(),
                    sentDTO.getTexts().get(i),
                    words.get(i))
            );
        }
    }
}
