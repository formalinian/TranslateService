package com.example.test.server.controllers;

import com.example.test.server.dto.ExceptionDTO;
import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.exceptions.ClientException;
import com.example.test.server.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    @PostMapping(path = "/translate")
    public ResponseEntity<OutgoingMessageDTO> getTranslatedMessage(@RequestBody IncomingMessageDTO incomingMessage,
                                                                   HttpServletRequest request) throws SQLException {
        String ipAddress = request.getRemoteAddr();
        return ResponseEntity.ok(translationService.sendTranslationRequest(incomingMessage, ipAddress));
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ExceptionDTO> exceptionHandler(ClientException clientException) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setCode(clientException.getCode());
        exceptionDTO.setMessage(clientException.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
}
