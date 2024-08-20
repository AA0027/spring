package org.example.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InviteCard {
    private String from;
    private String code;

    public InviteCard(String from, String code){
        this.from = from;
        this.code = code;
    }
}
