package org.example.com.service;

import org.example.com.domain.ChatRoom;
import org.example.com.domain.ChatUser;
import org.example.com.repo.ChatRoomRepository;
import org.example.com.repo.ChatUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, ChatUserRepository chatUserRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatUserRepository = chatUserRepository;
    }


    // 채팅방 생성
    public ChatRoom createChatRoom(String name){
        ChatRoom room = ChatRoom.createRoom(name);
        chatRoomRepository.save(room);
        return room;
    }
    // 이름으로 채팅방 조회
    public ChatRoom findChatRoom(Long id){
        return chatRoomRepository.findById(id).orElse(null);
    }

    // 전체 채팅방 조회
    public List<ChatRoom> getAllChatRoom(){return chatRoomRepository.findAll();}

    // 채팅 방삭제
    public void deleteChatRoom(String name){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByName(name);
        chatRoomRepository.delete(chatRoom);
    }

    // 채팅방 입장
    public ChatRoom enterChannel(Long url, Long userId){
        ChatRoom chatRoom = chatRoomRepository
                .findById(url).orElseThrow(() -> (new IllegalArgumentException("채널이 존재하지 않습니다.")));

        // 추가할 유저
        ChatUser chatUser = chatUserRepository.findById(userId)
                .orElseThrow(() -> (new IllegalArgumentException("유저가 존재하지 않습니다.")));

        List<ChatUser> list = chatRoom.getChatUsers();
        list.add(chatUser);

        return chatRoomRepository.save(chatRoom);
    }
    // 채팅방 퇴장
    public ChatRoom exitChannel(Long url, Long userId){
        ChatRoom chatRoom = chatRoomRepository
                .findById(url).orElseThrow(() -> (new IllegalArgumentException("채널이 존재하지 않습니다.")));

        // 제거할 유저
        ChatUser chatUser = chatUserRepository.findById(userId)
                .orElseThrow(() -> (new IllegalArgumentException("유저가 존재하지 않습니다.")));

        if(chatRoom.getChatUsers() == null){
            throw new IllegalArgumentException("퇴장할 유저가 없습니다.");
        }
        List<ChatUser> list = chatRoom.getChatUsers();
        if(list.remove(chatUser)){
            return chatRoomRepository.save(chatRoom);
        }
        else
            throw new IllegalArgumentException("삭제실패");
    }

    public void deleteRoom(ChatRoom chatRoom){
        chatRoomRepository.delete(chatRoom);
    }
}
