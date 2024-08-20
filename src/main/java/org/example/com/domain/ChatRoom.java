package org.example.com.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.com.repo.FindRoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String code;

    public static ChatRoom createRoom(String name){
        return ChatRoom.builder()
                .name(name)
                .code((UUID.randomUUID().toString()).replaceAll("-",""))
                .build();
    }


}
