package org.example.com.service;


import org.example.com.domain.ChatRoom;
import org.example.com.domain.Employee;
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
        List<Employee> lit = chatRoomService.getEmployeeInRoom("24303d34c96d4c1a9d94ed0e9e6e92be");
        lit.forEach(System.out::println);
    }

    @Test
    public void test1(){

        List<ChatRoom> list = chatRoomService.getMyChatRooms("alice01");
        System.out.println(list);
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
