package org.academo.academo.Exception;

import org.springframework.http.HttpStatus;

public class InvalidDataException extends CustomException{
    public InvalidDataException(String message, Throwable cause){
        super(message, cause, HttpStatus.BAD_REQUEST, "WARN");
    }
}
