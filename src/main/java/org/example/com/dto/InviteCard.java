package org.example.com.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.com.domain.ChatRoom;
import org.example.com.domain.Employee;

@Data
@NoArgsConstructor
public class InviteCard {
    private Employee from;
    private ChatRoom chatRoom;

    public InviteCard(Employee from, ChatRoom chatRoom){
        this.from = from;
        this.chatRoom = chatRoom;
    }
}
