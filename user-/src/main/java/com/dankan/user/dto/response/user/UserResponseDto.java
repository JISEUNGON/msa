package com.dankan.user.dto.response.user;

import com.dankan.user.domain.User;
import com.dankan.user.util.Model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private Boolean gender;
    private String phoneNumber;
    private String profileImg;
    private String univEmail;

    public static UserResponseDto of(final User user) {
        return Model.mapper.map(user, UserResponseDto.class);
    }
}
