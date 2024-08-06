package org.example.com.controller;

import org.example.com.domain.ChatRoom;
import org.example.com.dto.Channel;
import org.example.com.dto.CreateDto;
import org.example.com.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channel")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }
    // 내 채팅 목록 가져오기
    @PostMapping("/list")
    public ResponseEntity<?> getMyChannel(Channel channel){
        List<ChatRoom> list = chatRoomService.getMyChatRooms(channel.getUsername());
        if(list == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(list);
    }

    // 채팅방 생성
    @PostMapping("/newChannel")
    public ResponseEntity<?> createChannel(@RequestBody CreateDto createDto){
        chatRoomService.createChatRoom(createDto.getName(), createDto.getUsernames());
        List<ChatRoom> list = chatRoomService.getMyChatRooms(createDto.getMyId());
        if(list == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(list);
    }

    // 채팅방 참여
    @PostMapping("/enter")
    public ResponseEntity<?> enterChannel(@RequestBody Channel channel){
        ChatRoom chatRoom = chatRoomService.enterChannel(channel.getCode(), channel.getUsername());

        if(chatRoom == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok().build();
    }

    // 채팅방 나가기
    @PostMapping("/exit")
    public ResponseEntity<?> exitChannel(@RequestBody Channel channel){
        chatRoomService.exitChannel(channel.getCode(), channel.getUsername());
        return ResponseEntity.ok().build();
    }

    // 채팅방 삭제
    @PostMapping("/delete")
    public ResponseEntity<?> deleteChannel(@RequestBody Channel channel){
        chatRoomService.deleteChatRoom(channel.getCode());
        List<ChatRoom> list = chatRoomService.getMyChatRooms(channel.getUsername());
        if(list == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(list);
    }
}
