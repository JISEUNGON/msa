package com.dankan.userservice.service.user;

import com.dankan.userservice.domain.Authority;
import com.dankan.userservice.domain.DateLog;
import com.dankan.userservice.domain.Token;
import com.dankan.userservice.domain.User;
import com.dankan.userservice.dto.response.login.OauthLoginResponseDto;
import com.dankan.userservice.exception.token.TokenNotFoundException;
import com.dankan.userservice.exception.user.UserIdNotFoundException;
import com.dankan.userservice.exception.user.UserNameExistException;
import com.dankan.userservice.exception.user.UserNameNotFoundException;
import com.dankan.userservice.repository.DateLogRepository;
import com.dankan.userservice.repository.TokenRepository;
import com.dankan.userservice.repository.UserRepository;
import com.dankan.userservice.dto.response.login.LoginResponseDto;
import com.dankan.userservice.dto.response.logout.LogoutResponseDto;
import com.dankan.userservice.dto.response.user.UserResponseDto;
import com.dankan.userservice.util.JwtUtil;
import com.dankan.userservice.vo.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final DateLogRepository dateLogRepository;

    @Override
    public boolean checkDuplicatedName(String name) {
        final Optional<UserResponseDto> user = userRepository.findByNickname(name);

        if(user.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public UserResponseDto modifyNickName(final String name) {
        boolean isDuplicated = checkDuplicatedName(name);

        if(isDuplicated == true) {
            throw new UserNameExistException(name);
        }
        else
        {
            User user = userRepository.findById(JwtUtil.getMemberId()).orElseThrow(() -> new UserIdNotFoundException(JwtUtil.getMemberId().toString()));

            user.setNickname(name);

            userRepository.save(user);

            return UserResponseDto.of(user);
        }
    }

    @Override
    public UserResponseDto modifyProfileImg(final String imgUrl) {
        User user = userRepository.findById(JwtUtil.getMemberId()).orElseThrow(() -> new UserIdNotFoundException(JwtUtil.getMemberId().toString()));

        user.setProfileImg(imgUrl);

        userRepository.save(user);

        this.updateEvent(JwtUtil.getMemberId());

        return UserResponseDto.of(user);
    }

    @Override
    public Optional<User> checkDuplicatedEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public LoginResponseDto signUp(OauthLoginResponseDto oauthLoginResponseDto) {
        long id = System.currentTimeMillis();

        //권한
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        //날짜 로그
        DateLog dateLog = DateLog.of(id);

        User user = User.builder()
                .userId(id)
                .dateId(dateLogRepository.save(dateLog).getId())
                .authorities(Arrays.asList(authority))
                .email(oauthLoginResponseDto.getEmail())
                .nickname(oauthLoginResponseDto.getNickname())
                .profileImg(oauthLoginResponseDto.getProfileImg())
                .userType(0L) // 카카오 로그인, 그외 로그인 타입을 객체 지향적으로 분리해 of를 쓸 예정입니다. 더 고민해봐야해요.
                .build();
        userRepository.save(user);

        Token token = Token.of(user);
        tokenRepository.save(token);

        return LoginResponseDto.of(user,token);
    }

    @Override
    public LoginResponseDto signIn(User user) {
        Token token = tokenRepository.findByUserId(user.getUserId()).orElseThrow(() -> new UserIdNotFoundException(user.getUserId().toString()));

        return LoginResponseDto.of(user,token);
    }

    @Override
    public UserResponseDto findUser() {
        return userRepository.findByUserId(JwtUtil.getMemberId()).orElseThrow(
                () -> new UserIdNotFoundException(JwtUtil.getMemberId().toString())
        );
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findUserList();
    }

    @Override
    public UserResponseDto findUser(String name) {
        return userRepository.findByNickname(name).orElseThrow(
                () -> new UserNameNotFoundException(name)
        );
    }

    @Override
    public void deleteUser() {
        userRepository.deleteById(JwtUtil.getMemberId());

    }

    @Override
    public void deleteUser(final String name) {
        userRepository.delete(userRepository.findUserByNickname(name).orElseThrow(() -> new UserNameExistException(name)));
    }

    @Override
    public LogoutResponseDto logout() {
        String expiredAccessToken = JwtUtil.logout();

        Token token = tokenRepository.findByUserId(JwtUtil.getMemberId()).orElseThrow(() -> new TokenNotFoundException(JwtUtil.getMemberId().toString()));

        token.setAccessToken(expiredAccessToken);
        token.setAccessTokenExpiredAt(LocalDateTime.now().minusYears(1L));

        tokenRepository.save(token);

        return new LogoutResponseDto(expiredAccessToken);
    }

    @Override
    public List<String> getAuthorities() {
        return JwtUtil.getRoles();
    }

    @Override
    @CachePut(key = "#id", value = "userInfo")
    public UserInfo updateEvent(final Long id) {
        return userRepository.findName(id).orElseThrow();
    }
}
