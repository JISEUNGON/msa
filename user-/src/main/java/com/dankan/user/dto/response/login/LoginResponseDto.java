package com.dankan.user.dto.response.login;

import com.dankan.user.domain.Token;
import com.dankan.user.domain.User;
import com.dankan.user.util.Model;
import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private String nickname;
    private String email;
    private String profileImg;
    private String phoneNum;
    private Boolean gender;
    private Long userType;
    private String univEmail;

    public static LoginResponseDto of(final User user, final Token token) {
        LoginResponseDto responseDto = Model.mapper.map(user, LoginResponseDto.class);

        responseDto.setAccessToken(token.getAccessToken());
        responseDto.setRefreshToken(token.getRefreshToken());

        return responseDto;
    }
}
