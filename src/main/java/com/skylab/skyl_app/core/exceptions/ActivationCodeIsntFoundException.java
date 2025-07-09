package com.skylab.skyl_app.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ActivationCodeIsntFoundException extends RuntimeException {
    public ActivationCodeIsntFoundException(String message) {
        super(message);
    }

    public ActivationCodeIsntFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
