package org.example.com.dto;

import lombok.Data;

import java.util.List;

@Data
public class Invite {
    private String code;
    private List<String> usernames;
}
