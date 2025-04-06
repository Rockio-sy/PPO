package org.academo.academo.Exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException(String message, Throwable cause){
        super(message, cause, HttpStatus.NOT_FOUND, "WARN");
    }

    public ResourceNotFoundException(String message){
        super(message, null, HttpStatus.NOT_FOUND, "WARN");
    }
}
