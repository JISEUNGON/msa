package com.dankan.userservice.service.email;

import com.dankan.userservice.dto.request.email.EmailCodeRequestDto;

public interface EmailService {
    public String sendSimpleMessage(String to) throws Exception;
    public Boolean verifyCode(EmailCodeRequestDto emailCodeRequestDto);
}
