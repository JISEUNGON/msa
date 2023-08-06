package com.dankan.userservice.service.sms;

import com.dankan.userservice.dto.request.sns.CertificationRequestDto;

public interface SmsService {
    public void sendMessage(String phoneNum);
    public Boolean verifyNumber(CertificationRequestDto certificationRequestDto);
}
