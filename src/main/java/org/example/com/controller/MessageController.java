package org.example.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.com.domain.MessageLog;
import org.example.com.dto.ChatMessage;
import org.example.com.dto.InviteCard;
import org.example.com.dto.InviteDTO;
import org.example.com.service.ChatRoomService;
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

    private final ChatRoomService chatRoomService;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate, MessageLogService messageLogService, ChatRoomService chatRoomService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageLogService = messageLogService;
        this.chatRoomService = chatRoomService;
    }


    @MessageMapping("/{roomId}")
    public void chatting(@DestinationVariable String roomId, ChatMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        log.info(message.getUsername() + "메지지 들어옴");
        MessageLog messageLog = messageLogService.saveMessage(message);

        simpMessagingTemplate.convertAndSend("/sub/" + roomId, messageLog);

    }

    @MessageMapping("/invite/{username}")
    public void invite(@DestinationVariable String username, InviteDTO inviteDTO) throws Exception {
        Thread.sleep(1000); // simulated delay
        log.info(inviteDTO.getFrom() + "메지지 들어옴");

        InviteCard card = chatRoomService.inviteEmployee(inviteDTO);

        simpMessagingTemplate.convertAndSend("/sub/" + username, card);
    }

}