package org.example.com.controller;


import org.example.com.config.PrincipalDetails;
import org.example.com.domain.Employee;
import org.example.com.dto.UserDto;
import org.example.com.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomeController {
    private final EmployeeService employeeService;

    public HomeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @PostMapping("/login")
    public String login (@RequestBody UserDto userDto){
        return "hello";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin page";
    }

    @GetMapping("/member")
    public String member(){
        return "member page";
    }

    //-------------------------------------------------------------------------
    // 현재 Authentication 보기 (디버깅 등 용도로 활용)
    @RequestMapping("/auth")
    public Authentication auth() { // org.springframework.security.core.Authentication
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping("/user")
    public Employee user(@AuthenticationPrincipal PrincipalDetails userDetails){

        Employee e = (userDetails != null) ? userDetails.getUser() : null;
        Employee info = employeeService.findByUsername(e.getUsername());
        info.setRole(e.getRole());
        return info;
    }

}
