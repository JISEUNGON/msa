package com.dankan.userservice.controller;

import com.dankan.userservice.dto.request.email.EmailCodeRequestDto;
import com.dankan.userservice.dto.request.email.EmailRequestDto;
import com.dankan.userservice.dto.request.sns.CertificationRequestDto;
import com.dankan.userservice.dto.request.sns.SendMessageRequestDto;
import com.dankan.userservice.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/univ")
@RequiredArgsConstructor
@Slf4j
public class UnivController {
    private final EmailService emailService;

    @PostMapping("/mail")
    public ResponseEntity<String> mailConfirm(@RequestBody EmailRequestDto email) throws Exception {
        return ResponseEntity.ok(emailService.sendSimpleMessage(email.getEmail()));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Boolean> verifyEmailCode(@RequestBody EmailCodeRequestDto emailCodeRequestDto) {
        return ResponseEntity.ok(emailService.verifyCode(emailCodeRequestDto));
    }
}
