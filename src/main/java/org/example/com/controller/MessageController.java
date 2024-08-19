package org.example.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.com.domain.MessageLog;
import org.example.com.dto.ChatMessage;
import org.example.com.service.MessageLogService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageLogService messageLogService;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate, MessageLogService messageLogService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageLogService = messageLogService;
    }


    @MessageMapping("/{roomId}")
    public void greeting(@DestinationVariable String roomId, ChatMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        log.info(message.getUsername() + "메지지 들어옴");
        MessageLog messageLog = messageLogService.saveMessage(message);

        simpMessagingTemplate.convertAndSend("/sub/" + roomId, messageLog);

    }

}