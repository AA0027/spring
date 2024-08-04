package org.example.com.repo;

import org.example.com.domain.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    ChatUser findChatUsersByUserId(String userId);

}
