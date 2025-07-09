package com.skylab.skyl_app.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class ActivationCodeAlreadyConfirmedException extends RuntimeException {
    public ActivationCodeAlreadyConfirmedException(String message) {
        super(message);
    }

    public ActivationCodeAlreadyConfirmedException(String message, Throwable cause) {
        super(message, cause);
    }
}
