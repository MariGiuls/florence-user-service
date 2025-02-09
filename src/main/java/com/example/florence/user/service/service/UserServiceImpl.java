package com.example.florence.user.service.service;

import com.example.florence.user.service.repository.model.DBUser;
import com.example.florence.user.service.repository.service.IUserRepositoryService;
import it.florence.generate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepositoryService repositoryService;

    @Autowired
    public UserServiceImpl(IUserRepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public User save(User user) {
        DBUser user1 = repositoryService.save(DBUser.builder().build());
        return null;
    }

    @Override
    public User change(User user) {
        DBUser user1 = repositoryService.change(DBUser.builder().build());
        return null;
    }

    @Override
    public void delete(User user) {
        repositoryService.delete(DBUser.builder().build());
    }

    @Override
    public List<User> findAll() {
        repositoryService.findAll();
        return List.of();
    }

    @Override
    public User findById(String id) {
        DBUser user1 = repositoryService.findById(id).orElse(null);
        return null;
    }

    @Override
    public List<User> findBy(User user) {
        repositoryService.findBy(Example.of(DBUser.builder().build()));
        return List.of();
    }
}
