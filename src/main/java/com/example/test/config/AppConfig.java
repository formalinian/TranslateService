package com.example.test.config;

import com.example.test.server.mappers.IncomingMessageToSent;
import com.example.test.server.mappers.TranslatedMessageToOutgoing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    @Scope(scopeName = "prototype")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Scope(scopeName = "prototype")
    public IncomingMessageToSent incomingMessageToSent() {
        return new IncomingMessageToSent();
    }

    @Bean
    @Scope(scopeName = "prototype")
    public TranslatedMessageToOutgoing translatedMessageDTO() {
        return new TranslatedMessageToOutgoing();
    }

}
