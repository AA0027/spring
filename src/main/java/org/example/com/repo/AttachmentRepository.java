package org.example.com.repo;

import org.example.com.domain.Attachment;
import org.example.com.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    public Optional<List<Attachment>> findByChatRoom(ChatRoom chatRoom);
}
