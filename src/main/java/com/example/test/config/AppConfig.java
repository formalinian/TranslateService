package com.example.test.config;

import com.example.test.server.mappers.IncomingMessageToSent;
import com.example.test.server.mappers.TranslatedMessageToOutgoing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    @Bean
    @Scope(scopeName = "prototype")
    public Connection connection(Environment environment) {
        try {
            return DriverManager.getConnection(environment.getProperty("spring.datasource.url"),
                    environment.getProperty("spring.datasource.username"),
                    environment.getProperty("spring.datasource.password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
