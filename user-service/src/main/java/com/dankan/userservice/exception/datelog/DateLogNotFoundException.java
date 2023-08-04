package com.dankan.userservice.exception.datelog;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DateLogNotFoundException extends RuntimeException {

    private String message;

    public DateLogNotFoundException(Long id) {
        super(id.toString());
        this.message = id.toString();
    }
}
