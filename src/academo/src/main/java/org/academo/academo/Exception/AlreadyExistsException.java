package org.academo.academo.Exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends CustomException {
    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause, HttpStatus.CONFLICT, "WARN");
    }
}
