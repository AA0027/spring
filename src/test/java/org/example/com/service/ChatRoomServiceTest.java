package org.example.com.service;


import jakarta.transaction.Transactional;
import org.example.com.domain.ChatRoom;
import org.example.com.domain.Employee;
import org.example.com.domain.FindRoom;
import org.example.com.repo.ChatRoomRepository;
import org.example.com.repo.EmployeeRepository;
import org.example.com.repo.FindRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class ChatRoomServiceTest {

    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    FindRoomRepository findRoomRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void test(){
        ChatRoom chatRoom = chatRoomService.createChatRoom("test1",
                List.of("alice01", "bob02"));
        chatRoomService.createChatRoom("t2",
                List.of("alice01"));
        chatRoomService.createChatRoom("t3",
                List.of("alice01"));

        chatRoomService.createChatRoom("t4",
                List.of("alice01"));
    }

    @Test
    public void test1(){

//        List<ChatRoom> list = chatRoomService.getMyChatRooms("alice01");
//        System.out.println(list);
    }

    @Test
    public void test2(){
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach((e) -> {
            e.setPassword(passwordEncoder.encode("1111"));
        });
        employeeRepository.saveAll(employees);
    }

}
