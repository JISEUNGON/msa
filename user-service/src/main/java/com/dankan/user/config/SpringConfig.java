package com.dankan.user.config;

import com.dankan.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
public class SpringConfig {
    @Bean
    public UserService userService() {
        return null;
    }
}
