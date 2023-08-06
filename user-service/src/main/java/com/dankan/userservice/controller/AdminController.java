package com.dankan.userservice.controller;

import com.dankan.userservice.domain.Token;
import com.dankan.userservice.dto.response.univ.UnivListResponseDto;
import com.dankan.userservice.dto.response.user.UserResponseDto;
import com.dankan.userservice.service.token.TokenService;
import com.dankan.userservice.service.univ.UnivService;
import com.dankan.userservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final UserService userService;
    private final TokenService tokenService;
    private final UnivService  univService;

    @GetMapping("/user/info")
    public ResponseEntity<UserResponseDto> findUserByName(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(userService.findByNickname(name));
    }

    @GetMapping("/user/whole-info")
    public ResponseEntity<List<UserResponseDto>> checkDuplicatedNickname() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/token/info")
    public ResponseEntity<Token> getTokenInfo(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(tokenService.findByUserId(id));
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity deleteUser(@PathParam(value = "name") String name) {
        userService.deleteUser(name);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/univ/info")
    public ResponseEntity<List<UnivListResponseDto>> getUnivInfo() {
        return ResponseEntity.ok(univService.findAll());
    }
}
