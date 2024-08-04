package org.example.com.utils;

import org.example.com.domain.Attachment;
import org.example.com.repo.AttachmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@Service
public class FileService {
    @Value("${app.upload.path}")
    private String uploadDir;

    private final AttachmentRepository attachmentRepository;

    public FileService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public  void addFiles(Map<String, MultipartFile> files, Long id){
        if(files == null) return;
        for(Map.Entry<String, MultipartFile> e : files.entrySet()){
            if(!e.getKey().startsWith("upfile")) continue;

            Attachment file = upload(e.getValue());

            if(file != null){
                // FK 설정 필요
                attachmentRepository.save(file);
            }

        }
    }

    public  Attachment upload(MultipartFile multipartFile){
        Attachment attachment = null;
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");


        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename == null || originalFilename.isEmpty()) return null;

        String sourceName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String fileName = sourceName;

        File file = new File(uploadDir, fileName);
        if(file.exists()){
            int pos = fileName.lastIndexOf(".");
            if(pos > -1){
                String name = fileName.substring(0, pos);
                String ext = fileName.substring(pos+1);


                fileName = name + "_" + uuid + "." + ext;
            }else {
                fileName += "_" + uuid;
            }
        }

        Path copyOfLocation = Paths.get(new File(uploadDir, fileName).getAbsolutePath());

        try {
            Files.copy(
                    multipartFile.getInputStream(),
                    copyOfLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        attachment = Attachment.builder()
                .filename(fileName)
                .sourcename(sourceName)
                .build();

        return attachment;
    }
}
