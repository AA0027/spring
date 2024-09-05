package org.example.com.controller;

import org.example.com.domain.ChatRoom;
import org.example.com.domain.Employee;
import org.example.com.dto.*;
import org.example.com.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channel")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }


    // 내 채팅 목록 가져오기
    @PostMapping("/list")
    public ResponseEntity<?> getMyChannel(@RequestBody Channel channel){
        List<ChatRoom> list = chatRoomService.getMyChatRooms(channel.getUsername());
        if(list == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(list);
    }

    // 나의 초대 목록 가져오기
    @PostMapping("/my-invite")
    public ResponseEntity<?> getMyInvites(@RequestBody Channel channel){
        List<InviteCard> cards = chatRoomService.getInviteList(channel);
        if(cards == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(cards);
    }

    // 초대 수락
    @PostMapping("/accept")
    public ResponseEntity<?> accept(@RequestBody Channel channel){
        chatRoomService.acceptInvite(channel);

        return ResponseEntity.ok().build();
    }
    // 초대 거절
    @PostMapping("/reject")
    public ResponseEntity<?> reject(@RequestBody Channel channel){
        chatRoomService.rejectInvite(channel);
        return ResponseEntity.ok().build();
    }

    // 채팅방 생성
    @PostMapping("/newChannel")
    public ResponseEntity<?> createChannel(@RequestBody CreateDto createDto){
        ChatRoom chatRoom =
                chatRoomService.createChatRoom(createDto.getName(), createDto.getMyId());
        if(chatRoom == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(chatRoom);
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
    // 채팅방내 사용자 검색
    @PostMapping("/attendee")
    public ResponseEntity<?> getAttendeeList(@RequestBody Channel channel){
        List<Employee> list = chatRoomService.getEmployeeInRoom(channel.getCode());
        if(list == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(list);
    }


}
