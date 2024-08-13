package org.example.com.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.com.jwt.JWTUtil;
import org.example.com.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class FilterChannelInterceptor implements ChannelInterceptor {
        private final JWTUtil jwtUtil;
        private final EmployeeService employeeService;

    public FilterChannelInterceptor(JWTUtil jwtUtil, EmployeeService employeeService) {
        this.jwtUtil = jwtUtil;
        this.employeeService = employeeService;
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if(headerAccessor == null){
            return message;
        }

        if(headerAccessor.getCommand() == StompCommand.CONNECT){
            String token = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
            token = token.replace("Bearer ", "");
            token = token.replace("[", "");
            token = token.replace("]", "");
            System.out.println("token");

            // 유저 정보 추가
            String username = jwtUtil.getUsername(token);

            if(employeeService.findByUsername(username) == null){
                return null;
            }
        }
        return message;
    }
}
