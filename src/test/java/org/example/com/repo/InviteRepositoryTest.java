package org.example.com.repo;

import org.example.com.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class InviteRepositoryTest {
    @Autowired
    InviteRepository inviteRepository;


    @Test
    public void test(){

        inviteRepository.deleteByCode("17a0b28aaab5480e865cc09550c9ae3c");

    }
}