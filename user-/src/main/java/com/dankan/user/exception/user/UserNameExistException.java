package com.dankan.user.exception.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNameExistException extends RuntimeException {
    private String message;

    public UserNameExistException(String name) {
        super(name);
        message = name;
    }
}
