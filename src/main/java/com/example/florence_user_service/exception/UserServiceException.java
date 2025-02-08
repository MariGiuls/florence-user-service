package com.example.florence_user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class UserServiceException extends HttpClientErrorException {

    public UserServiceException(String message, HttpStatus status, String statusText) {
        super(message, status, statusText, null, null, null);
    }

}
