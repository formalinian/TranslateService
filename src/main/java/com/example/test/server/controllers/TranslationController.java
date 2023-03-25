package com.example.test.server.controllers;

import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.services.TranslationMessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TranslationController {
    private final TranslationMessageService translationMessageService;

    public TranslationController(@Autowired TranslationMessageService translationMessageService) {
        this.translationMessageService = translationMessageService;
    }

    @PostMapping(path = "/translate")
    public ResponseEntity<OutgoingMessageDTO> getTranslatedMessage(@RequestBody IncomingMessageDTO incomingMessage,
                                                                   HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        return ResponseEntity.ok(translationMessageService.sendTranslationRequest(incomingMessage, ipAddress));
    }
}
