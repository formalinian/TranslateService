package com.example.test.server.mappers;

import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.entities.RequestDataEntity;

import java.time.LocalDateTime;

public class RequestDataMapper {
    public RequestDataEntity transformToReqDataEntity(IncomingMessageDTO incomingMessageDTO, OutgoingMessageDTO outgoingMessageDTO, String ipAddress) {
        RequestDataEntity requestDataEntity = new RequestDataEntity();
        requestDataEntity.setIncomingMessage(incomingMessageDTO.getText());
        requestDataEntity.setTranslatedMessage(outgoingMessageDTO.getText());
        requestDataEntity.setSourceLanguage(incomingMessageDTO.getSourceLanguageCode());
        requestDataEntity.setTargetLanguage(incomingMessageDTO.getTargetLanguageCode());
        requestDataEntity.setRequestTime(LocalDateTime.now().toString());
        requestDataEntity.setRequestIp(ipAddress);
        return requestDataEntity;
    }
}
