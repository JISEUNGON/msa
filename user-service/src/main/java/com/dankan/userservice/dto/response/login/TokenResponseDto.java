package com.dankan.userservice.dto.response.login;

import com.dankan.userservice.domain.Token;
import com.dankan.userservice.util.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private LocalDateTime accessTokenExpiredAt;
    private LocalDateTime refreshTokenExpiredAt;

    public static TokenResponseDto of(final Token token) {
        return Model.mapper.map(token, TokenResponseDto.class);
    }
}
