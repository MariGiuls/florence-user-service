package com.example.florence.user.service.exception;

import org.springframework.http.HttpStatus;

public class UserServiceException extends UserException {
    public UserServiceException(String message, HttpStatus status) {
        super(message, status);
    }
}
