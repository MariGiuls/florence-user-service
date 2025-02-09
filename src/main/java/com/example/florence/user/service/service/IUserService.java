package com.example.florence.user.service.service;

import it.florence.generate.model.User;

import java.util.List;

public interface IUserService {
    User save(User user);
    public User change(User user);
    void delete(User user);
    List<User> findAll();
    User findById(String id);
    List<User> findBy(User user);
}
