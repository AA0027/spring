package org.example.com.controller;


import org.example.com.config.PrincipalDetails;
import org.example.com.domain.Employee;
import org.example.com.dto.UserDto;
import org.example.com.service.AuthService;
import org.example.com.service.EmployeeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@RequestMapping("/api")
public class HomeController {
    private final EmployeeService employeeService;
    private final AuthService authService;

    public HomeController(EmployeeService employeeService, AuthService authService) {
        this.employeeService = employeeService;
        this.authService = authService;
    }

    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) throws UserPrincipalNotFoundException {
        String token = authService.login(username, password);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return new ResponseEntity<>(headers, HttpStatus.OK);
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
