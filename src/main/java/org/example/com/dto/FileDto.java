package org.example.com.dto;

import lombok.Data;

import java.util.List;

@Data
public class FileDto {
    private List<Long> fileID;
    private String username;
    private String code;
    private String regdate;
}
