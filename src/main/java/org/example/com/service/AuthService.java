package org.example.com.service;

import org.example.com.config.PrincipalDetails;
import org.example.com.config.PrincipalDetailsService;
import org.example.com.domain.Employee;
import org.example.com.dto.UserDto;
import org.example.com.jwt.JWTUtil;
import org.example.com.repo.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
public class AuthService {
    private final JWTUtil jwtUtil;
    private final PrincipalDetailsService principalDetailsService;
    public AuthService(JWTUtil jwtUtil, PrincipalDetailsService principalDetailsService) {
        this.jwtUtil = jwtUtil;
        this.principalDetailsService = principalDetailsService;
    }

    public String login(String username, String password) throws UserPrincipalNotFoundException {

        PrincipalDetails user = (PrincipalDetails) principalDetailsService
                .loadUserByUsername(username);

        if(user == null){
            throw new UserPrincipalNotFoundException("해당 유저가 없습니다.");
        }

        return jwtUtil.createJwt(user.getUser().getId()
                , username, "ROLE_MEMBER", 30 * 60 * 1000L);
    }


}
