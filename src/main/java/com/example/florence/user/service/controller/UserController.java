package com.example.florence.user.service.controller;

import com.example.florence.user.service.exception.UserException;
import com.example.florence.user.service.service.IUserService;
import it.florence.generate.api.UserApi;
import it.florence.generate.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
@RestController
public class UserController implements UserApi {

    private final IUserService userService;

    @Autowired
    public UserController(@Qualifier("userServiceCSVImpl")IUserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<User> user(User body) {
        ResponseEntity<User> response;
        log.info("user save request: " + body);
        try {
            User user = userService.save(body);
            response = ResponseEntity.ok(user);
        } catch (UserException e) {
            log.error("error: " + e.getMessage());
            response = new ResponseEntity<>(e.getStatusCode());
        }
        return response;
    }

    @Override
    public ResponseEntity<User> changeUser(User body) {
        ResponseEntity<User> response;
        log.info("user change request: " + body);
        try {
            User user = userService.change(body);
            response = ResponseEntity.ok(user);
        } catch (UserException e) {
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
        } catch (UserException e) {
            log.error("error: " + e.getMessage());
            response = new ResponseEntity<>(e.getStatusCode());
        }
        return response;
    }

    public ResponseEntity<User> getUserById(BigDecimal id) {
        ResponseEntity<User> response;
        log.info("user find by id request: " + id);
        try {
            User user = userService.findById(id);
            response = ResponseEntity.ok(user);
        } catch (UserException e) {
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
        } catch (UserException e) {
            log.error("error: " + e.getMessage());
            response = new ResponseEntity<>(e.getStatusCode());
        }
        return response;
    }

    @Override
    public ResponseEntity<Void> uploadUsersCsv(MultipartFile file) {
        ResponseEntity<Void> response;
        log.info("uploadUsersCsv user request: " + file.getContentType());
        try {
            userService.uploadUsersCsv(file);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (UserException e) {
            log.error("error: " + e.getMessage());
            response = new ResponseEntity<>(e.getStatusCode());
        }
        return response;
    }
}
