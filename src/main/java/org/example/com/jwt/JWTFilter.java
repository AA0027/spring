package org.example.com.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.com.config.PrincipalDetails;
import org.example.com.domain.Employee;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        if(jwtUtil.isExpired(token)){
            filterChain.doFilter(request, response);
            return;
        }

        Long id = jwtUtil.getId(token);
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRoles(token);

        Employee employee = Employee.builder()
                .id(id)
                .username(username)
                .role(role)
                .password("temp_password")
                .build();

        PrincipalDetails userDetails = new PrincipalDetails(employee);
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
