package com.dankan.userservice.exception.token;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenNotFoundException extends RuntimeException {
    private String message;

    public TokenNotFoundException(String userId) {
        super(userId);
        message = userId.toString() + " is not exist";
    }
}
