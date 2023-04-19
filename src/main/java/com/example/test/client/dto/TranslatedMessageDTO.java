package com.example.test.client.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TranslatedMessageDTO {

    @JsonSetter("translations")
    private List<TranslatedWordDTO> translatedWordDTOS;

}
