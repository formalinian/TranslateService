package com.example.test.server.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.entities.RequestDataEntity;
import com.example.test.server.entities.RequestWordEntity;
import com.example.test.server.mappers.RequestDataMapper;
import com.example.test.server.mappers.RequestWordMapper;
import com.example.test.server.repositories.RequestDataRepo;
import com.example.test.server.repositories.WordsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final RequestDataRepo requestDataRepo;
    private final WordsRepo wordsRepo;
    private final RequestWordMapper requestWordMapper;
    private final RequestDataMapper requestDataMapper;

    public void saveIntoDB(IncomingMessageDTO incomingMessageDTO,
                           OutgoingMessageDTO outgoingMessageDTO,
                           SentDTO sentDTO,
                           String ipAddress) throws SQLException {
        RequestDataEntity requestDataEntity = requestDataMapper.transformToReqDataEntity(incomingMessageDTO, outgoingMessageDTO, ipAddress);
        requestDataRepo.saveMessage(requestDataEntity);
        List<String> words = new ArrayList<>();
        if (outgoingMessageDTO.getText() != null) {
            words = Arrays.stream(outgoingMessageDTO.getText().split(" ")).toList();
        }
        List<RequestWordEntity> entities = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            entities.add(requestWordMapper.transformToReqWordEntity(
                    (long) i + 1,
                    requestDataEntity.getId(),
                    sentDTO.getTexts().get(i),
                    words.get(i))
            );
        }
        wordsRepo.saveWords(entities);
    }
}
