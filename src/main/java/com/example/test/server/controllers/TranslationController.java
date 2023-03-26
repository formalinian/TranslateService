package com.example.test.server.controllers;

import com.example.test.server.dto.IncomingMessageDTO;
import com.example.test.server.dto.OutgoingMessageDTO;
import com.example.test.server.dto.ExceptionDTO;
import com.example.test.server.exceptions.CustomException;
import com.example.test.server.services.TranslationRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TranslationController {
    private final TranslationRequestService translationRequestService;

    public TranslationController(@Autowired TranslationRequestService translationRequestService) {
        this.translationRequestService = translationRequestService;
    }

    @PostMapping(path = "/translate")
    public ResponseEntity<OutgoingMessageDTO> getTranslatedMessage(@RequestBody IncomingMessageDTO incomingMessage,
                                                                   HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        return ResponseEntity.ok(translationRequestService.sendTranslationRequest(incomingMessage, ipAddress));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionDTO> exceptionHandler(CustomException customException) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setCode(customException.getCode());
        exceptionDTO.setMessage(customException.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
}
