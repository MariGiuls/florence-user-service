package com.example.florence_user_service.exception;

import org.springframework.http.HttpStatus;

public class DBUserException extends UserServiceException {

    public DBUserException(String message, HttpStatus status) {
        super(message, status, status.getReasonPhrase());
    }
}
