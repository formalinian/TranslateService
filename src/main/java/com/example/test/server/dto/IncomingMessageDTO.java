package com.example.test.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IncomingMessageDTO {
    private String text;
    private String sourceLanguageCode;
    private String targetLanguageCode;
}
