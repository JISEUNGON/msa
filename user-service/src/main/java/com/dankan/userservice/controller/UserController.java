package com.dankan.userservice.controller;

import com.dankan.userservice.dto.request.email.EmailCodeRequestDto;
import com.dankan.userservice.dto.request.email.EmailRequestDto;
import com.dankan.userservice.dto.request.sns.CertificationRequestDto;
import com.dankan.userservice.dto.request.sns.SendMessageRequestDto;
import com.dankan.userservice.dto.response.logout.LogoutResponseDto;
import com.dankan.userservice.dto.response.user.UserResponseDto;
import com.dankan.userservice.service.email.EmailService;
import com.dankan.userservice.service.s3.S3UploadService;
import com.dankan.userservice.service.sms.SmsService;
import com.dankan.userservice.service.user.UserService;
import com.dankan.userservice.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final S3UploadService s3UploadService;
    private final SmsService smsService;
    private final EmailService emailService;

    @GetMapping("/nickname")
    public ResponseEntity<Boolean> checkDuplicatedNickname(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(userService.checkDuplicatedName(name));
    }

    @GetMapping("/modify-nickname")
    public ResponseEntity<UserResponseDto> modifyNickName(@RequestParam(value = "nickname") String nickname) {
        final UserResponseDto response = userService.modifyNickName(nickname);

        //cache update
        userService.updateEvent(JwtUtil.getMemberId());

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/profileImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponseDto> modifyProfileImg(@RequestParam(value = "image") MultipartFile multipartFile) throws IOException {
        String imgUrl = s3UploadService.upload(multipartFile, "profile");
        final UserResponseDto response = userService.modifyProfileImg(imgUrl);

        userService.updateEvent(JwtUtil.getMemberId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> getUserInfo() {
        return ResponseEntity.ok(userService.findUser());
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser() {
        userService.deleteUser();

        /**
         * TODO: delete 됐을 때 관련 항목들 삭제 + 캐쉬 업데이트
         * */

        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout() {
        return ResponseEntity.ok(userService.logout());
    }


    @PostMapping("/message")
    public ResponseEntity sendIdentifyMessage(@RequestBody SendMessageRequestDto SendMessageRequestDto) {
        smsService.sendMessage(SendMessageRequestDto.getPhoneNumber());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyUser(@RequestBody CertificationRequestDto certificationRequestDto) {
        return ResponseEntity.ok(smsService.verifyNumber(certificationRequestDto));
    }
}
