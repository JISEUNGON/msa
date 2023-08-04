package com.dankan.userservice.controller;

import com.dankan.userservice.dto.request.token.TokenRequestDto;
import com.dankan.userservice.dto.response.login.TokenResponseDto;
import com.dankan.userservice.service.token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @GetMapping("check")
    public ResponseEntity<Boolean> isExpiredAt() {
        return ResponseEntity.ok(tokenService.isExpired());
    }

    @PostMapping("reissue")
    public ResponseEntity<TokenResponseDto> reissueAccessToken(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(tokenService.reissueAccessToken(tokenRequestDto));
    }
}
