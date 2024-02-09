package com.log.gains.exception;

public class EmailNotValidException extends RuntimeException{
    public EmailNotValidException (String message) {
        super(message);
    }
}
