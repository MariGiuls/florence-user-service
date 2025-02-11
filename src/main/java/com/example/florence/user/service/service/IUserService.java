package com.example.florence.user.service.service;

import it.florence.generate.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface IUserService {
    User save(User user);
    User change(User user);
    void delete(User user);
    List<User> findAll();
    User findById(BigDecimal id);
    List<User> findBy(User user);
}
