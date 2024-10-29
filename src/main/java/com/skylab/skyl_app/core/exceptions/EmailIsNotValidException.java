package com.skylab.skyl_app.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailIsNotValidException extends RuntimeException {
    public EmailIsNotValidException(String message) {
        super(message);
    }

    public EmailIsNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
