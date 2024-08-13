package org.example.com.service;

import org.example.com.dto.ChatMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MessageLogServiceTest {
    @Autowired
    MessageLogService messageLogService;
    @Test
    public void test(){
        ChatMessage chatMessage = ChatMessage.builder()
                .code("aa")
                .username("cho")
                .content("test1").build();
        messageLogService.saveMessage(chatMessage);
    }
}