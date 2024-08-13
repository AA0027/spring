package org.example.com.service;

import org.example.com.domain.Employee;
import org.example.com.domain.MessageLog;
import org.example.com.dto.ChatMessage;
import org.example.com.excep.NoSuchDataException;
import org.example.com.repo.EmployeeRepository;
import org.example.com.repo.MessageLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageLogService {

    private final MessageLogRepository messageLogRepository;
    private final EmployeeRepository employeeRepository;

    public MessageLogService(MessageLogRepository messageLogRepository, EmployeeRepository employeeRepository) {
        this.messageLogRepository = messageLogRepository;
        this.employeeRepository = employeeRepository;
    }

    // 메시지 조회
    public List<MessageLog> getMessages(String code){
        return messageLogRepository.findMessageLogsByCodeOrderByRegdate(code)
                .orElseThrow(() -> new NoSuchDataException("데이터가 없습니다."));
    }

    public MessageLog saveMessage(ChatMessage chatMessage){
        Employee employee = employeeRepository.findEmployeeByUsername(chatMessage.getUsername());

        MessageLog messageLog = MessageLog.builder()
                .sender(employee)
                .code(chatMessage.getCode())
                .content(chatMessage.getContent())
                .regdate(chatMessage.getRegdate())
                .build();

        return messageLogRepository.save(messageLog);
    }

}
