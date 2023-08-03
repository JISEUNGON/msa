package com.dankan.user.controller;

import com.dankan.user.dto.response.LogoutResponseDto;
import com.dankan.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/ping")
    public String pong() {
        return "pong";
    }

    @GetMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout() {
        return ResponseEntity.ok(userService.logout());
    }
}
