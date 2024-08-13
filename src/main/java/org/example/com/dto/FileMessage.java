package org.example.com.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileMessage {
    private String name;
    private MultipartFile file;
}
