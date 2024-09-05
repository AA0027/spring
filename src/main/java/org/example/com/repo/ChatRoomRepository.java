package org.example.com.repo;

import org.example.com.domain.ChatRoom;
import org.example.com.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findChatRoomByName(String name);
    Optional<ChatRoom> findByCode(String code);

}
