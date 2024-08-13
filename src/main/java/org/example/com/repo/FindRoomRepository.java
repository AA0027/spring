package org.example.com.repo;

import org.example.com.domain.ChatRoom;
import org.example.com.domain.Employee;
import org.example.com.domain.FindRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FindRoomRepository extends JpaRepository<FindRoom, Long> {
    void deleteByChatRoomAndEmployee(ChatRoom chatRoom, Employee employee);

    Optional<List<FindRoom>> findByChatRoom(ChatRoom chatRoom);
    Optional<List<FindRoom>> findByEmployee(Employee employee);

    Optional<FindRoom> findByChatRoomAndEmployee(ChatRoom chatRoom, Employee employee);
}
