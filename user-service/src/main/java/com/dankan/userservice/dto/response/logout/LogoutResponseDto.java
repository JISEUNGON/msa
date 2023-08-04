package com.dankan.userservice.dto.response.logout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogoutResponseDto {
    private String accessToken;
}