package com.example.test.server.repositories;

import com.example.test.server.entities.RequestWordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class RequestWordRepo {

    public static final String INSERT_REQUEST =
            "INSERT INTO REQUEST_WORD VALUES(?, ?, ?)";
    private final Connection connection;

    public RequestWordRepo(@Autowired Connection connection) {
        this.connection = connection;
    }

    public void saveWord(RequestWordEntity requestWordEntity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REQUEST)) {
            preparedStatement.setLong(1, requestWordEntity.getIdRequest());
            preparedStatement.setString(2, requestWordEntity.getIncomingWord());
            preparedStatement.setString(3, requestWordEntity.getTranslatedWord());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
