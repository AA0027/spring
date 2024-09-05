package org.example.com.service;

import lombok.extern.slf4j.Slf4j;
import org.example.com.domain.Employee;
import org.example.com.dto.UserDto;
import org.example.com.excep.NoSuchDataException;
import org.example.com.repo.EmployeeRepository;
import org.example.com.type.Dept;
import org.example.com.type.Position;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 유저 생성
    public void createUser(UserDto userDto){
        Employee employee = Employee
                .builder()
                .name("조창성")
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role("ROLE_MEMBER")
                .position(Position.Director)
                .dept(Dept.Tech01)
                .build();

        employeeRepository.save(employee);
    }

    // 아이디로 직원찾기
    public Employee findByUsername(String username){return employeeRepository.findEmployeeByUsername(username);}

    // 부서로 직원들 찾기
    public List<Employee> findEmployeeByDept(String dept){
        List<Employee> list = new ArrayList<>();
        switch(dept){
            case "Tech01":
                list = employeeRepository.findEmployeeByDept(Dept.Tech01)
                        .orElseThrow(() -> new NoSuchDataException("해당 데이터가 없습니다."));
                break;
            case "Tech02":
                list = employeeRepository.findEmployeeByDept(Dept.Tech02)
                        .orElseThrow(() -> new NoSuchDataException("해당 데이터가 없습니다."));
                break;
            case "Tech03":
                list = employeeRepository.findEmployeeByDept(Dept.Tech03)
                        .orElseThrow(() -> new NoSuchDataException("해당 데이터가 없습니다."));
                break;
            case "QA" :
                list = employeeRepository.findEmployeeByDept(Dept.QA)
                        .orElseThrow(() -> new NoSuchDataException("해당 데이터가 없습니다."));
                break;
            default:
                list = null;
                break;
        }

        return list;
    }

    // 검색창으로 직원조회
    public List<Employee> searchEmployee(String word){

        List<Employee> list = employeeRepository.findEmployeeByNameContainsIgnoreCase(word)
                .orElseThrow(() -> new NoSuchDataException("해당 데이터가 없습니다."));

        return list;
    }

    // 전체 직원
    public List<Employee> findAll(){
        return employeeRepository.findEmployees()
            .orElseThrow(() -> new NoSuchDataException("해당 데이커가 없습니다."));}
}
