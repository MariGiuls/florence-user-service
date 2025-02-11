package com.example.florence.user.service.exception;

import org.springframework.http.HttpStatus;

public class DBUserException extends UserException {

    public DBUserException(String message, HttpStatus status) {
        super(message, status);
    }
}
