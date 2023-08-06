package com.dankan.userservice.service.user;

import com.dankan.userservice.domain.User;
import com.dankan.userservice.dto.response.login.LoginResponseDto;
import com.dankan.userservice.dto.response.login.OauthLoginResponseDto;
import com.dankan.userservice.dto.response.logout.LogoutResponseDto;
import com.dankan.userservice.dto.response.user.UserResponseDto;
import com.dankan.userservice.vo.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public boolean checkDuplicatedName(String name);
    public UserResponseDto modifyNickName(String name);
    public UserResponseDto modifyProfileImg(String imgUrl);
    public Optional<User> checkDuplicatedEmail(String name);
    public LoginResponseDto signUp(OauthLoginResponseDto oauthLoginResponseDto);
    public LoginResponseDto signIn(User user);
    public UserResponseDto findUser();
    public List<UserResponseDto> findAll();
    public UserResponseDto findByNickname(String name);
    public void deleteUser();
    public void deleteUser(String name);
    public LogoutResponseDto logout();
    public List<String> getAuthorities();
    public UserInfo updateEvent(Long id);
}
