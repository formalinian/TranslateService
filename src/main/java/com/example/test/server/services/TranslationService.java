package com.example.test.server.services;

import java.sql.SQLException;

public interface TranslationService<O, I, R> {
    O translate(I incomingMessage, R requestIpAddress) throws SQLException;
}
