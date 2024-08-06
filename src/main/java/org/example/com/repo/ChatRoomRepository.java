package org.example.com.repo;

import org.example.com.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findChatRoomByName(String name);
    Optional<ChatRoom> findByCode(String code);
}
