package com.dankan.userservice.service.token;

import com.dankan.userservice.domain.Token;
import com.dankan.userservice.dto.request.token.TokenRequestDto;
import com.dankan.userservice.dto.response.login.TokenResponseDto;

public interface TokenService {
    public Boolean isExpired();
    public TokenResponseDto reissueAccessToken(TokenRequestDto tokenRequestDto);
    public Token findByUserId(Long id);
}
