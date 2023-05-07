package com.example.test.server.services;

import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;

import java.sql.SQLException;

public interface TranslationService {
    OutgoingMessageDTO translate(IncomingMessageDTO incomingMessage, String requestIpAddress) throws SQLException;
}
