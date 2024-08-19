package org.example.com.utils;

import org.example.com.domain.Attachment;
import org.example.com.dto.FileMessage;
import org.example.com.repo.AttachmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
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

        attachmentRepository.save(attachment);
        return attachment;
    }


    public ResponseEntity<?> download(Long id){
        if(id == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Attachment file = attachmentRepository.findById(id).orElse(null);

        if(file == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        String sourceName = file.getSourcename();
        String fileName = file.getFilename();

        String path = new File(uploadDir, fileName).getAbsolutePath();


        try {
            String mimeType = Files.probeContentType(Paths.get(path));

            if(mimeType == null)
                mimeType = "application/octet-stream";

            Path filePath = Paths.get(path);

            Resource resource = new InputStreamResource(Files.newInputStream(filePath));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                            .filename(URLEncoder.encode(sourceName, "utf-8"))
                    .build());
            headers.setCacheControl("no-cache");
            headers.setContentType(MediaType.parseMediaType(mimeType));

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);
        }
    }
}
