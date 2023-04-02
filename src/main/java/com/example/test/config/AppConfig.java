package com.example.test.config;

import com.example.test.server.mappers.OutgoingMessageMapper;
import com.example.test.server.mappers.RequestDataMapper;
import com.example.test.server.mappers.RequestWordMapper;
import com.example.test.server.mappers.SentDtoMapper;
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
    public Connection connection(Environment environment) {
        try {
            return DriverManager.getConnection(environment.getProperty("spring.datasource.url"),
                    environment.getProperty("spring.datasource.username"),
                    environment.getProperty("spring.datasource.password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean
    @Scope(scopeName = "prototype")
    public SentDtoMapper sentDtoMapper(){return new SentDtoMapper();}

    @Bean
    @Scope(scopeName = "prototype")
    public OutgoingMessageMapper outgoingMessageMapper(){return new OutgoingMessageMapper();}

    @Bean
    @Scope(scopeName = "prototype")
    public RequestDataMapper requestDataMapper(){return new RequestDataMapper();}

    @Bean
    @Scope(scopeName = "prototype")
    public RequestWordMapper requestWordMapper(){return new RequestWordMapper();}
}
