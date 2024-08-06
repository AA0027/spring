package org.example.com.controller;

import org.example.com.domain.Employee;
import org.example.com.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 모든 사용자 정보
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<Employee> list = employeeService.findAll();
        if(list == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return ResponseEntity.ok(list);
    }
    // 부서별 정보찾기
    @GetMapping("/search")
    public ResponseEntity<?> getEmployees(@RequestParam String dept){
        List<Employee> list = employeeService.findEmployeeByDept(dept);
        if(list == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return ResponseEntity.ok(list);
    }
}
