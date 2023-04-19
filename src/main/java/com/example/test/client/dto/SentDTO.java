package com.example.test.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SentDTO {
    private List<String> texts;
    private String targetLanguageCode;
    private String sourceLanguageCode;
}
