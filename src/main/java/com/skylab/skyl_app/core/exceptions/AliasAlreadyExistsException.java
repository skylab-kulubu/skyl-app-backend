package com.skylab.skyl_app.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AliasAlreadyExistsException extends RuntimeException {
    public AliasAlreadyExistsException(String message) {
        super(message);
    }

    public AliasAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
