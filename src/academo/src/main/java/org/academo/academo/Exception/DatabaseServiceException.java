package org.academo.academo.Exception;

import org.springframework.http.HttpStatus;

public class DatabaseServiceException extends CustomException {
    public DatabaseServiceException(String message, Throwable cause) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR, "ERROR");
    }
}
