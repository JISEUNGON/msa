package com.dankan.user.controller;

import com.dankan.user.domain.User;
import com.dankan.user.dto.response.login.LoginResponseDto;
import com.dankan.user.dto.response.login.OauthLoginResponseDto;
import com.dankan.user.service.login.OAuthService;
import com.dankan.user.service.login.SocialLoginType;
import com.dankan.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class LoginController {
    private final OAuthService oauthService;
    private final UserService userService;

    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        oauthService.request(socialLoginType);
    }

    @GetMapping(value = "/{socialLoginType}/token")
    public ResponseEntity<LoginResponseDto> codeCallBack(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestParam(name = "code") String code) {
        OauthLoginResponseDto oauthLoginResponseDto = oauthService.getLoginResponseDto(socialLoginType,code);

        Optional<User> result = userService.checkDuplicatedEmail(oauthLoginResponseDto.getEmail());

        if(result.isEmpty()) {
            return ResponseEntity.ok(userService.signUp(oauthLoginResponseDto));
        }
        else
        {
            return ResponseEntity.ok(userService.signIn(result.get()));
        }
    }
}
