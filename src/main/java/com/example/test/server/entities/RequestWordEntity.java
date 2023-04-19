package com.example.test.server.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestWordEntity {
    private Long id;
    private Long idRequest;
    private String incomingWord;
    private String translatedWord;
}
