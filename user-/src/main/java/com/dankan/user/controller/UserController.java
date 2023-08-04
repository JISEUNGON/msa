package com.dankan.user.controller;

import com.dankan.user.dto.response.logout.LogoutResponseDto;
import com.dankan.user.dto.response.user.UserResponseDto;
import com.dankan.user.service.s3.S3UploadService;
import com.dankan.user.service.user.UserService;
import com.dankan.user.util.JwtUtil;
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

    @GetMapping("/ping")
    public String pong() {
        return "pong";
    }

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
}
