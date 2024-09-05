package org.example.com.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    private Employee creator;



    public static ChatRoom createRoom(String name, Employee creator){
        return ChatRoom.builder()
                .name(name)
                .creator(creator)

                .code((UUID.randomUUID().toString()).replaceAll("-",""))
                .build();
    }


}
