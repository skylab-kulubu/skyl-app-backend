package com.skylab.skyl_app.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserDoesntExistsException extends RuntimeException {
    public UserDoesntExistsException(String message) {
        super(message);
    }

    public UserDoesntExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
