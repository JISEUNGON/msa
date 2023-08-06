package com.dankan.userservice.service.token;

import com.dankan.userservice.domain.Token;
import com.dankan.userservice.dto.request.token.TokenRequestDto;
import com.dankan.userservice.dto.response.login.TokenResponseDto;
import com.dankan.userservice.exception.token.TokenNotFoundException;
import com.dankan.userservice.repository.TokenRepository;
import com.dankan.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public Boolean isExpired() {
        return JwtUtil.isExpired(JwtUtil.getAccessToken());
    }

    @Override
    public TokenResponseDto reissueAccessToken(final TokenRequestDto tokenRequestDto) {
        String accessToken = JwtUtil.getAccessToken();

        Token token = tokenRepository.findTokenByAccessTokenAndRefreshToken(accessToken, JwtUtil.getRefreshToken()).orElseThrow(
                () -> new TokenNotFoundException(tokenRequestDto.getUserId().toString())
        );
        log.info("token1 called");

        token.setAccessToken(
                JwtUtil.createJwt(tokenRequestDto.getUserId()));

        log.info("token2 called");

        token.setAccessTokenExpiredAt(LocalDateTime.now().plusDays(JwtUtil.ACCESS_TOKEN_EXPIRE_TIME));

        tokenRepository.save(token);

        return TokenResponseDto.builder()
                .userId(tokenRequestDto.getUserId())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .accessTokenExpiredAt(token.getAccessTokenExpiredAt())
                .refreshTokenExpiredAt(token.getRefreshTokenExpiredAt())
                .build();
    }

    @Override
    public Token findByUserId(final Long id) {
        return tokenRepository.findByUserId(id).orElseThrow(() -> new TokenNotFoundException(id.toString()));
    }
}
