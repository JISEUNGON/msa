package com.dankan.user.dto.response.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthLoginResponseDto {
    private String nickname;
    private String email;
    private String profileImg;
    private String phoneNum;
    private Boolean gender;
    private String univEmail;
}
