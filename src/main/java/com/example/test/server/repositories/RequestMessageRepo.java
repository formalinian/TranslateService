package com.example.test.server.repositories;

import com.example.test.server.entities.RequestMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class RequestMessageRepo {

    public static final String INSERT_REQUEST =
            "INSERT INTO REQUEST_MESSAGE(INCOMING_MESSAGE, " +
                    "TRANSLATED_MESSAGE, " +
                    "SOURCE_LANGUAGE, " +
                    "TARGET_LANGUAGE, " +
                    "REQUEST_TIME, " +
                    "REQUEST_IP) VALUES(?, ?, ?, ?, ?, ?)";
    private final Connection connection;

    public RequestMessageRepo(@Autowired Connection connection) {
        this.connection = connection;
    }

    public void saveMessage(RequestMessageEntity requestMessageEntity){
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, requestMessageEntity.getIncomingMessage());
            preparedStatement.setString(2, requestMessageEntity.getTranslatedMessage());
            preparedStatement.setString(3, requestMessageEntity.getSourceLanguage());
            preparedStatement.setString(4, requestMessageEntity.getTargetLanguage());
            preparedStatement.setString(5, requestMessageEntity.getRequestTime());
            preparedStatement.setString(6, requestMessageEntity.getRequestIp());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            requestMessageEntity.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
