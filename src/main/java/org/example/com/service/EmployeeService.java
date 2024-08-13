package org.example.com.service;

import org.example.com.domain.Employee;
import org.example.com.excep.NoSuchDataException;
import org.example.com.repo.EmployeeRepository;
import org.example.com.type.Dept;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // 유저 생성
    public Employee createUser(Employee employee){return employeeRepository.save(employee);};

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
