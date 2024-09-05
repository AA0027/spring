package org.example.com.repo;

import org.example.com.domain.Attendee;
import org.example.com.domain.ChatRoom;
import org.example.com.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    Optional<List<Attendee>> findByEmployee(Employee employee);

    Optional<List<Attendee>> findByChatRoom(ChatRoom chatRoom);

    void deleteByChatRoomAndEmployee(ChatRoom chatRoom, Employee employee);
}
