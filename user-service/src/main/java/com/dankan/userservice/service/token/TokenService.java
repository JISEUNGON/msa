package com.dankan.userservice.service.token;

import com.dankan.userservice.dto.request.token.TokenRequestDto;
import com.dankan.userservice.dto.response.login.TokenResponseDto;

public interface TokenService {
    public Boolean isExpired();
    public TokenResponseDto reissueAccessToken(TokenRequestDto tokenRequestDto);
    public TokenResponseDto findByUserId(Long id);
}
