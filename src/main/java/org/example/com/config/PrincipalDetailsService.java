package org.example.com.config;


import org.example.com.domain.Employee;
import org.example.com.service.EmployeeService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final EmployeeService employeeService;


    public PrincipalDetailsService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeService.findByUsername(username);

        if(employee != null){
            return new PrincipalDetails(employee);
        }

        throw new UsernameNotFoundException(username);
    }
}
