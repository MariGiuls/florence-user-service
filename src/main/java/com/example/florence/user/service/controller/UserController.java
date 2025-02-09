package com.example.florence.user.service.controller;

import com.example.florence.user.service.exception.UserServiceException;
import com.example.florence.user.service.service.IUserService;
import it.florence.generate.api.UserApi;
import it.florence.generate.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
public class UserController implements UserApi {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> user(User body) {
        ResponseEntity<Void> response;
        log.info("user save request: " + body);
        try {
            userService.save(body);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (UserServiceException e) {
            log.error("error: " + e.getMessage());
            response = new ResponseEntity<>(e.getStatusCode());
        }
        return response;
    }

    @Override
    public ResponseEntity<Void> changeUser(User body) {
        ResponseEntity<Void> response;
        log.info("user change request: " + body);
        try {
            userService.change(body);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (UserServiceException e) {
            log.error("error: " + e.getMessage());
            response = new ResponseEntity<>(e.getStatusCode());
        }
        return response;
    }

    @Override
    public ResponseEntity<List<User>> getUser(User body) {
        ResponseEntity<List<User>> response;
        log.info("user find by request: " + body);
        try {
            List<User> users = userService.findBy(body);
            response = ResponseEntity.ok(users);
        } catch (UserServiceException e) {
            log.error("error: " + e.getMessage());
            response = new ResponseEntity<>(e.getStatusCode());
        }
        return response;
    }

    @Override
    public ResponseEntity<User> getUserById(String id) {
        ResponseEntity<User> response;
        log.info("user find by id request: " + id);
        try {
            User user = userService.findById(id);
            response = ResponseEntity.ok(user);
        } catch (UserServiceException e) {
            log.error("error: " + e.getMessage());
            response = new ResponseEntity<>(e.getStatusCode());
        }
        return response;
    }

    @Override
    public ResponseEntity<Void> deleteUser(User body) {
        ResponseEntity<Void> response;
        log.info("delete user request: " + body);
        try {
            userService.delete(body);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (UserServiceException e) {
            log.error("error: " + e.getMessage());
            response = new ResponseEntity<>(e.getStatusCode());
        }
        return response;
    }
}
