package org.example.com.config;


import lombok.extern.slf4j.Slf4j;
import org.example.com.domain.Employee;
import org.example.com.service.EmployeeService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final EmployeeService employeeService;


    public PrincipalDetailsService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 처리 시작");
        Employee employee = employeeService.findByUsername(username);

        if(employee != null){
            log.error("해당하는 유저가 없습니다.");
            return new PrincipalDetails(employee);
        }
        log.info("사용자: " + employee.toString());
        log.info("로그인 처리 끝");
        throw new UsernameNotFoundException(username);
    }
}
