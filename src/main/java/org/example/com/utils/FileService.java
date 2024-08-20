package org.example.com.utils;

import org.example.com.domain.Attachment;
import org.example.com.domain.ChatRoom;
import org.example.com.domain.Employee;
import org.example.com.domain.FindRoom;
import org.example.com.dto.FileDto;
import org.example.com.dto.FileMessage;
import org.example.com.excep.NoSuchDataException;
import org.example.com.repo.AttachmentRepository;
import org.example.com.repo.ChatRoomRepository;
import org.example.com.repo.EmployeeRepository;
import org.example.com.repo.FindRoomRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FileService {
    @Value("${app.upload.path}")
    private String uploadDir;

    private final AttachmentRepository attachmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final FindRoomRepository findRoomRepository;
    public FileService(AttachmentRepository attachmentRepository, EmployeeRepository employeeRepository, ChatRoomRepository chatRoomRepository, FindRoomRepository findRoomRepository) {
        this.attachmentRepository = attachmentRepository;
        this.employeeRepository = employeeRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.findRoomRepository = findRoomRepository;
    }
    // 파일들 가져오기
    public List<Attachment> getChatRoomFiles(String code){
        List<Attachment> list = new ArrayList<>();

        ChatRoom chatRoom = chatRoomRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchDataException("chatRoom 이 존재하지 않습니다. "));
        List<FindRoom> findRoomList = findRoomRepository.findByChatRoom(chatRoom)
                .orElseThrow(() -> new NoSuchDataException("FindRoom 이 존재하지 않습니다. "));

        findRoomList.forEach(e -> {
            list.addAll(e.getFiles());
        });
        return list;
    }
    // 채팅방에 파일 저장
    public void saveFile(FileDto fileDto){
        Employee employee = employeeRepository.findEmployeeByUsername(fileDto.getUsername());
        ChatRoom chatRoom = chatRoomRepository.findByCode(fileDto.getCode())
                .orElseThrow(() -> new NoSuchDataException("chatRoom 이 존재하지 않습니다. "));
        List<Attachment> attachmentList = fileDto.getFileID().stream().map(id -> attachmentRepository
                .findById(id).orElseThrow(() -> new NoSuchDataException("Attachment 가 존재하지 않습니다."))).toList();

        FindRoom findRoom = findRoomRepository.findByChatRoomAndEmployee(chatRoom, employee)
                .orElseThrow(() -> new NoSuchDataException("FindRoom 이 존재하지 않습니다. "));
        List<Attachment> list = findRoom.getFiles();
        list.addAll(attachmentList);
        findRoom.setFiles(list);
        findRoomRepository.save(findRoom);
    }
    // 물리적인 파일 저장
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

    // 파일 데이터다운로드
    public ResponseEntity<?> download(Long id){
        if(id == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Attachment file = attachmentRepository.findById(id).orElse(null);

        if(file == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        String sourceName = file.getSourcename();
        String fileName = file.getFilename();

        String path = new File(uploadDir, fileName).getAbsolutePath();


        try {
//            String mimeType = Files.probeContentType(Paths.get(path));
//
//            if(mimeType == null)
//                mimeType = "application/octet-stream";

            String mimeType = "application/octet-stream";
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
