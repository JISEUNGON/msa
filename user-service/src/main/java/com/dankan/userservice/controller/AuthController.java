package com.dankan.userservice.controller;

import com.dankan.userservice.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(("/auth"))
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/ping")
    public String pong() {
        return "pong";
    }

    @GetMapping("/check")
    public ResponseEntity<List<String>> getAuthorities() {
        return ResponseEntity.ok(userService.getAuthorities());
    }
}
