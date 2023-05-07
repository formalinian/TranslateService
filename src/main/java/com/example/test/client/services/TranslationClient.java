package com.example.test.client.services;

public interface TranslationClient<T, S> {
    T sendTranslationRequest(S sentDTO);
}
