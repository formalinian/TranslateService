package com.example.test.server.mappers;

import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.entities.RequestDataEntity;
import com.example.test.server.entities.RequestWordEntity;

import java.time.LocalDateTime;

public class RequestWordMapper {
    public RequestWordEntity transformToReqWordEntity(long id, long idRequest, String incomingWord, String translatedWord){
        RequestWordEntity requestWordEntity = new RequestWordEntity();
        requestWordEntity.setId(id);
        requestWordEntity.setIdRequest(idRequest);
        requestWordEntity.setIncomingWord(incomingWord);
        requestWordEntity.setTranslatedWord(translatedWord);
        return requestWordEntity;
    }
}
