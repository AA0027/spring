package org.example.com.controller;

import org.example.com.dto.ChatMessage;
import org.example.com.service.MessageLogService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageLogService messageLogService;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate, MessageLogService messageLogService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageLogService = messageLogService;
    }


    @MessageMapping("/{roomId}")
    public void greeting(@DestinationVariable Long roomId, ChatMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay

        messageLogService.saveMessage(message);

        simpMessagingTemplate.convertAndSend("/sub/" + roomId, message);

    }

}