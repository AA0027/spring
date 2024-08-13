package org.example.com.repo;

import org.example.com.domain.Employee;
import org.example.com.excep.NoSuchDataException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;
    @Test
    public void test(){
        List<String> ids = List.of("alice01","kara11","oscar15","rick18");
        List<Employee> list = employeeRepository.findEmpByList(ids)
                .orElseThrow(() -> new NoSuchDataException("데이터가 없습니다."));
        list.forEach(System.out::println);

    }
}