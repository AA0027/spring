package org.example.com.utils;

import org.example.com.dto.FileDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FileServiceTest {
    @Autowired
    FileService fileService;
    @Test
    public void test(){
        FileDto fileDto = new FileDto();
        fileDto.setFileID(List.of(1L, 2L, 3L));
        fileDto.setCode("214f66c6b13a44309e5625808737c28b");
        fileDto.setUsername("bob02");
        fileService.saveFile(fileDto);
    }
}