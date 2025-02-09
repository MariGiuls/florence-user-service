package com.example.florence.user.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class UserServiceException extends HttpClientErrorException {

    public UserServiceException(String message, HttpStatus status) {
        super(message, status, status.getReasonPhrase(), null, null, null);
    }

}
