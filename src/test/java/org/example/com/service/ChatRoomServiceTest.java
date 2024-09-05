package org.example.com.service;


import org.example.com.domain.ChatRoom;
import org.example.com.domain.Employee;
import org.example.com.domain.Invite;
import org.example.com.dto.Channel;
import org.example.com.dto.InviteCard;
import org.example.com.repo.ChatRoomRepository;
import org.example.com.repo.EmployeeRepository;
import org.example.com.repo.InviteRepository;
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
    ChatRoomRepository chatRoomRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    InviteRepository inviteRepository;
    @Test
    public void test(){

        chatRoomService.createChatRoom("hello","alice01");
//        System.out.println(chatRoomRepository.findAll());
//        chatRoomService.deleteChatRoom("26234ae07727414991cef6f488cae9b9");
    }

    @Test
    public void test1(){


    }

    @Test
    public void test2(){
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach((e) -> {
            e.setPassword(passwordEncoder.encode("1111"));
        });
        employeeRepository.saveAll(employees);
    }

    @Test
    public void test03(){
        Employee to = employeeRepository.findEmployeeByUsername("alice01");
        Employee from = employeeRepository.findEmployeeByUsername("bob02");

        Invite invite = Invite.builder()
                .to(to)
                .from(from)
                .code("65b6115c193c4989b39e23faf399634b")
                .build();

        inviteRepository.save(invite);
    }

    @Test
    public void test04(){

    }
}
