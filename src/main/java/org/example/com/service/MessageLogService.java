package org.example.com.service;

import org.example.com.domain.*;
import org.example.com.dto.ChatMessage;
import org.example.com.dto.FileDto;
import org.example.com.excep.NoSuchDataException;
import org.example.com.repo.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageLogService {

    private final MessageLogRepository messageLogRepository;
    private final EmployeeRepository employeeRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final FindRoomRepository findRoomRepository;
    private final AttachmentRepository attachmentRepository;

    public MessageLogService(MessageLogRepository messageLogRepository, EmployeeRepository employeeRepository, ChatRoomRepository chatRoomRepository, FindRoomRepository findRoomRepository, AttachmentRepository attachmentRepository) {
        this.messageLogRepository = messageLogRepository;
        this.employeeRepository = employeeRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.findRoomRepository = findRoomRepository;
        this.attachmentRepository = attachmentRepository;
    }

    // 메시지 조회
    public List<MessageLog> getMessages(String code){
        return messageLogRepository.findMessageLogsByCodeOrderByRegdate(code)
                .orElseThrow(() -> new NoSuchDataException("데이터가 없습니다."));
    }

    public MessageLog saveMessage(ChatMessage chatMessage){
        Employee employee = employeeRepository.findEmployeeByUsername(chatMessage.getUsername());

        if(chatMessage.getFiles() == null){
            System.out.println("첨부된 파일이 없습니다.");
            // TODO
            // 에러처리
        }
        List<Attachment> list = (chatMessage.getFiles()).stream()
                .map((f) -> attachmentRepository.findById(f.getId()).orElse(null)).toList();

        MessageLog messageLog = MessageLog.builder()
                .sender(employee)
                .code(chatMessage.getCode())
                .content(chatMessage.getContent())
                .type("file")
                .files(list)
                .regdate(chatMessage.getRegdate())
                .build();

        return messageLogRepository.save(messageLog);
    }

//    public List<Attachment> getFiles(String code){
//
//    }

}
