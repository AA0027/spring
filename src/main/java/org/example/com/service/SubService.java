package org.example.com.service;

import org.example.com.domain.Employee;
import org.example.com.domain.Sub;
import org.example.com.dto.SubDTO;
import org.example.com.excep.NoSuchDataException;
import org.example.com.repo.EmployeeRepository;
import org.example.com.repo.SubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubService {

    private final EmployeeRepository employeeRepository;
    private final SubRepository subRepository;

    public SubService(EmployeeRepository employeeRepository, SubRepository subRepository) {
        this.employeeRepository = employeeRepository;
        this.subRepository = subRepository;
    }

    // 구독
    public void subscribe(SubDTO subDTO){
        Employee employee = employeeRepository.findEmployeeByUsername(subDTO.getUsername());
        Sub sub = Sub.builder()
                .subId(subDTO.getSubId())
                .code(subDTO.getCode())
                .build();

        subRepository.save(sub);
        List<Sub> list = employee.getSubs();
        list.add(sub);
        employee.setSubs(list);
        employeeRepository.save(employee);
    }


    // 구독 취소
    public void unSub(String subId){
        Sub sub = subRepository.deleteBySubId(subId);
        if(sub == null)
            throw new NoSuchDataException("해당 데이터가 없습니다.");
    }

    // 구독 번호 조회
    public String getSubId(SubDTO subDTO){
        Employee employee = employeeRepository.findEmployeeByUsername(subDTO.getUsername());
        List<Sub> subs = employee.getSubs();
        for(Sub s : subs){
            if(s.getCode().equals(subDTO.getCode()))
                return s.getSubId();
        }
        return null;
    }

    // 구독 목록 가져오기
    public List<Sub> getSubList(SubDTO subDTO){
        Employee employee = employeeRepository.findEmployeeByUsername(subDTO.getUsername());
        return employee.getSubs();
    }

}
