package org.example.com.config;

import org.example.com.jwt.JWTUtil;
import org.example.com.service.EmployeeService;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    private final JWTUtil jwtUtil;
    private final EmployeeService employeeService;

    public WebSocketConfig(JWTUtil jwtUtil, EmployeeService employeeService) {
        this.jwtUtil = jwtUtil;
        this.employeeService = employeeService;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메세지 구독하는 여청의 prefix
        config.enableSimpleBroker("/api/sub");
        
        // 메세지 발행하는 요청의 prefix
        config.setApplicationDestinationPrefixes("/api/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/channel")
                .setAllowedOrigins("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new FilterChannelInterceptor(jwtUtil, employeeService));
    }
}
