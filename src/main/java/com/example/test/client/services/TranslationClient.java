package com.example.test.client.services;

import com.example.test.client.dto.SentDTO;
import com.example.test.client.dto.TranslatedMessageDTO;

public interface TranslationClient<T, S> {
    T sentTranslationRequest(S sentDTO);
}
