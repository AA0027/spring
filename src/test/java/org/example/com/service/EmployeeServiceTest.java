package org.example.com.service;

import org.example.com.repo.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmployeeServiceTest {
    @Autowired
    EmployeeRepository employeeRepository;
    @Test
    void findByUsername() {
//        Employee employee = employeeRepository.findEmployeeByUsername("bob02");
//        System.out.println(employee);
    }
}