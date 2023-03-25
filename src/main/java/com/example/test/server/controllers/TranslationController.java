package com.example.test.server.controllers;

import com.example.test.client.dtos.SentDTO;
import com.example.test.client.dtos.TranslatedMessageDTO;
import com.example.test.client.services.YaTranslationService;
import com.example.test.server.dtos.IncomingMessageDTO;
import com.example.test.server.dtos.OutgoingMessageDTO;
import com.example.test.server.services.TranslationMessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TranslationController {
    private TranslationMessageService translationMessageService;

    public TranslationController(@Autowired TranslationMessageService translationMessageService) {
        this.translationMessageService = translationMessageService;
    }

    @PostMapping(path = "/translate")
    public ResponseEntity<OutgoingMessageDTO> getTranslatedMessage(@RequestBody IncomingMessageDTO incomingMessage) {
        OutgoingMessageDTO outgoingMessage = translationMessageService.translate(incomingMessage);
        return ResponseEntity.ok(outgoingMessage);
    }
}
