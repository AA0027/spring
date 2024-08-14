package org.example.com.controller;

import org.example.com.dto.FileMessage;
import org.example.com.utils.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
//
//    @PostMapping("/file")
//    public String files(@RequestBody FileMessage fileMessage){
//        fileService.addFiles();
//    }
}
