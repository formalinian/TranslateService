package com.example.test.server.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDataEntity {
    private Long id;
    private String incomingMessage;
    private String translatedMessage;
    private String sourceLanguage;
    private String targetLanguage;
    private String requestTime;
    private String requestIp;
}
