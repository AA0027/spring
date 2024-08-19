package org.example.com.controller;

import org.example.com.domain.Attachment;
import org.example.com.dto.FileDto;
import org.example.com.dto.FileMessage;
import org.example.com.utils.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/download")
    public ResponseEntity<?> getFile(@RequestParam(required = false) Long id){
        return fileService.download(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> addFile(@RequestPart(value = "files", required = false) MultipartFile[] files) throws NoSuchFileException {
        System.out.println();
        List<Attachment> attachmentList = new ArrayList<>();
        for(MultipartFile file : files){
            attachmentList.add(fileService.upload(file));
        }

        if(attachmentList.isEmpty())
            throw new NoSuchFileException("저장에 실패 했습니다.");
        return ResponseEntity.ok(attachmentList);
    }

    @GetMapping("/list")
    public ResponseEntity<?> chatRoomFiles(@RequestParam(required = false) String code){
        return null;
    }
}
