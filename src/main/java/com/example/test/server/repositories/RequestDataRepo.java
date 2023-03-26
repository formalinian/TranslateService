package com.example.test.server.repositories;

import com.example.test.server.entities.RequestDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class RequestDataRepo {

    public static final String INSERT_REQUEST =
            "INSERT INTO REQUEST_DATA(INCOMING_MESSAGE, " +
                    "TRANSLATED_MESSAGE, " +
                    "SOURCE_LANGUAGE, " +
                    "TARGET_LANGUAGE, " +
                    "REQUEST_TIME, " +
                    "REQUEST_IP) VALUES(?, ?, ?, ?, ?, ?)";
    private final Connection connection;

    public RequestDataRepo(@Autowired Connection connection) {
        this.connection = connection;
    }

    public void saveMessage(RequestDataEntity requestDataEntity){
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, requestDataEntity.getIncomingMessage());
            preparedStatement.setString(2, requestDataEntity.getTranslatedMessage());
            preparedStatement.setString(3, requestDataEntity.getSourceLanguage());
            preparedStatement.setString(4, requestDataEntity.getTargetLanguage());
            preparedStatement.setString(5, requestDataEntity.getRequestTime());
            preparedStatement.setString(6, requestDataEntity.getRequestIp());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            requestDataEntity.setId(resultSet.getLong("ID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
