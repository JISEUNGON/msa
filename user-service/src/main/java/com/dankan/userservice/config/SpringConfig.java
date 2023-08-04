package com.dankan.userservice.config;

import com.amazonaws.services.s3.AmazonS3;
import com.dankan.userservice.repository.DateLogRepository;
import com.dankan.userservice.repository.TokenRepository;
import com.dankan.userservice.repository.UserRepository;
import com.dankan.userservice.service.s3.S3UploadService;
import com.dankan.userservice.service.s3.S3UploaderServiceImpl;
import com.dankan.userservice.service.token.TokenService;
import com.dankan.userservice.service.token.TokenServiceImpl;
import com.dankan.userservice.service.user.UserService;
import com.dankan.userservice.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SpringConfig {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final DateLogRepository dateLogRepository;
    private final AmazonS3 amazonS3;

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository, tokenRepository, dateLogRepository);
    }

    @Bean
    public S3UploadService s3UploadService() {
        return new S3UploaderServiceImpl(amazonS3);
    }

    @Bean
    public TokenService tokenService() {
        return new TokenServiceImpl(tokenRepository);
    }
}
