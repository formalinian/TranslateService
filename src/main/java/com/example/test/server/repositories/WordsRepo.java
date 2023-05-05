package com.example.test.server.repositories;

import com.example.test.server.dto.ExceptionDTO;
import com.example.test.server.entities.RequestWordEntity;
import com.example.test.server.exceptions.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WordsRepo {

    public static final String INSERT_REQUEST =
            "INSERT INTO REQUEST_WORD VALUES(?, ?, ?, ?)";
    private final Connection connection;

    public void saveWords(List<RequestWordEntity> entities) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REQUEST)) {
            connection.setAutoCommit(false);
            for (int i = 0; i < entities.size(); i++) {
                preparedStatement.setLong(1, entities.get(i).getId());
                preparedStatement.setLong(2, entities.get(i).getIdRequest());
                preparedStatement.setString(3, entities.get(i).getIncomingWord());
                preparedStatement.setString(4, entities.get(i).getTranslatedWord());
                preparedStatement.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            ExceptionDTO responseBodyAs = new ExceptionDTO();
            responseBodyAs.setMessage(e.getClass().getSimpleName());
            responseBodyAs.setCode(e.getErrorCode());
            throw new ClientException(responseBodyAs.getMessage(), responseBodyAs.getCode());
        } catch (Exception e){
            connection.rollback();
        }
    }
}
