package org.academo.academo.Exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String logLevel;
    private final Throwable cause;

    public CustomException(String error, Throwable cause, HttpStatus httpStatus, String logLevel) {
        super(error);
        this.cause = cause;
        this.httpStatus = httpStatus;
        this.logLevel = logLevel;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public Throwable getCause() {
        return cause;
    }
}
