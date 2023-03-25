package com.example.test.server.repositories;

import com.example.test.server.entities.RequestMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public class RequestMessageRepo {

    private Connection connection;

    public RequestMessageRepo(@Autowired Connection connection) {
        this.connection = connection;
    }

    public void logMessage(RequestMessageEntity requestMessageEntity){
        //connection.prepareStatement();
    }
}
