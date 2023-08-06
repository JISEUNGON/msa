package com.dankan.userservice.dto.request.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCodeRequestDto {
    private String email;
    private String code;
}
