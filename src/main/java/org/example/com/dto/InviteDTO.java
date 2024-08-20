package org.example.com.dto;

import lombok.Data;

import java.util.List;

@Data
public class InviteDTO {
    private String code;
    private String from;
    private String to;
}
