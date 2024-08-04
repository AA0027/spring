package org.example.com.service;

import org.example.com.domain.ChatUser;
import org.example.com.repo.ChatUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatUserService {
    private final ChatUserRepository chatUserRepository;

    public ChatUserService(ChatUserRepository chatUserRepository) {
        this.chatUserRepository = chatUserRepository;
    }

    // 유저 생성
    public ChatUser createUser(ChatUser chatUser){return chatUserRepository.save(chatUser);};

}
