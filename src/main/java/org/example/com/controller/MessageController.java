package org.example.com.controller;

import org.example.com.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @MessageMapping("/{roomId}")
    public void greeting(@DestinationVariable Long roomId, ChatMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay



        simpMessagingTemplate.convertAndSend("/sub/" + roomId, message);

    }

}