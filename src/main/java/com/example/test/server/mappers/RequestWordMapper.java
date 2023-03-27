package com.example.test.server.mappers;

import com.example.test.server.entities.RequestWordEntity;

public class RequestWordMapper {
    public RequestWordEntity transformToReqWordEntity(long id, long idRequest, String incomingWord, String translatedWord) {
        RequestWordEntity requestWordEntity = new RequestWordEntity();
        requestWordEntity.setId(id);
        requestWordEntity.setIdRequest(idRequest);
        requestWordEntity.setIncomingWord(incomingWord);
        requestWordEntity.setTranslatedWord(translatedWord);
        return requestWordEntity;
    }
}
