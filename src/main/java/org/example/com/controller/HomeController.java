package org.example.com.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.com.config.PrincipalDetails;
import org.example.com.domain.Employee;
import org.example.com.dto.UserDto;
import org.example.com.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api")
public class HomeController {
    private final EmployeeService employeeService;

    public HomeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
//        this.authService = authService;
    }

    @GetMapping("/")
    public String home() {
        return "Home";
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) throws UserPrincipalNotFoundException {
//        String token = authService.login(username, password);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + token);
//        return new ResponseEntity<>(headers, HttpStatus.OK);
//    }


    @PostMapping("join")
    public ResponseEntity<?> join(@RequestBody UserDto userDto){
        employeeService.createUser(userDto);
        return ResponseEntity.ok().build();
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
