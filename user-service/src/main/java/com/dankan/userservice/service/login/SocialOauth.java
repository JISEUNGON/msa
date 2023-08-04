package com.dankan.userservice.service.login;

import com.dankan.userservice.dto.response.login.OauthLoginResponseDto;

public interface SocialOauth {
    String getOauthRedirectURL();
    String getAccessToken(String code) ;
    OauthLoginResponseDto getLoginResponseDto(String idToken);
    default SocialLoginType type() {
        if (this instanceof KakaoOauth) {
            return SocialLoginType.kakao;
        } else {
            return null;
        }
    }
}
