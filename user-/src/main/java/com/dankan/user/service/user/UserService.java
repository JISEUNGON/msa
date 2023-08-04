package com.dankan.user.service.user;

import com.dankan.user.domain.Authority;
import com.dankan.user.domain.User;
import com.dankan.user.dto.response.login.LoginResponseDto;
import com.dankan.user.dto.response.login.OauthLoginResponseDto;
import com.dankan.user.dto.response.logout.LogoutResponseDto;
import com.dankan.user.dto.response.user.UserResponseDto;
import com.dankan.user.vo.UserInfo;

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
    public UserResponseDto findUser(String name);
    public void deleteUser();
    public void deleteUser(String name);
    public LogoutResponseDto logout();
    public List<String> getAuthorities();
    public UserInfo updateEvent(Long id);
}
