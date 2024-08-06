package org.example.com.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateDto {
    private String name;
    private String myId;
    private List<String> usernames;
}
