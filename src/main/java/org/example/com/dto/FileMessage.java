package org.example.com.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Data
public class FileMessage {
    private Map<String, MultipartFile> files;
}
