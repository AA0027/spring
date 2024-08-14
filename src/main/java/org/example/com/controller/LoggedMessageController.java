package org.example.com.controller;

import org.example.com.domain.MessageLog;
import org.example.com.dto.Channel;
import org.example.com.service.MessageLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class LoggedMessageController {

    private final MessageLogService messageLogService;

    public LoggedMessageController(MessageLogService messageLogService) {
        this.messageLogService = messageLogService;
    }

    @PostMapping("/list")
    public ResponseEntity<?> getMessages(@RequestBody Channel channel){
        List<MessageLog> list = messageLogService.getMessages(channel.getCode());
        if(list == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(list);
    }

}
