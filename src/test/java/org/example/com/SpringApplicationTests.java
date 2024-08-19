package org.example.com;

import org.example.com.domain.Employee;
import org.example.com.repo.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class SpringApplicationTests {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
//        List<Employee> list = employeeRepository.findAll();
//        list.forEach(e -> {
//            e.setPassword(passwordEncoder.encode("1111"));
//            employeeRepository.save(e);
//        });
    }

}
