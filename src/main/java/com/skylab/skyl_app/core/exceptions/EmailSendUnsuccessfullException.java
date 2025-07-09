package com.skylab.skyl_app.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailSendUnsuccessfullException extends RuntimeException {
    public EmailSendUnsuccessfullException(String message) {
        super(message);
    }

    public EmailSendUnsuccessfullException(String message, Throwable cause) {
        super(message, cause);
    }
}
