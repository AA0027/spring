package org.example.com.controller;

import org.example.com.domain.Attachment;
import org.example.com.dto.FileDto;
import org.example.com.utils.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // 파일 다운로드
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
    // 채팅방에 파일 저장
    @PostMapping("/save")
    public ResponseEntity<?> saveFiles(@RequestBody FileDto fileDto){
        fileService.saveFile(fileDto);
        return ResponseEntity.ok().build();
    }

    // 채팅방 파일 불러오기
    @GetMapping("/list")
    public ResponseEntity<?> getChatRoomFiles(@RequestParam String code)
    {
        List<Attachment> attachmentList = fileService.getChatRoomFiles(code);
        if(attachmentList == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(attachmentList);

    }
}
