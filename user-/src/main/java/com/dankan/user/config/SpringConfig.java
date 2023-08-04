package com.dankan.user.config;

import com.amazonaws.services.s3.AmazonS3;
import com.dankan.user.repository.DateLogRepository;
import com.dankan.user.repository.TokenRepository;
import com.dankan.user.repository.UserRepository;
import com.dankan.user.service.s3.S3UploadService;
import com.dankan.user.service.s3.S3UploaderServiceImpl;
import com.dankan.user.service.user.UserService;
import com.dankan.user.service.user.UserServiceImpl;
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
}
