package com.example.florence.user.service.service;

import com.example.florence.user.service.exception.UserServiceException;
import com.example.florence.user.service.repository.model.DBUser;
import com.example.florence.user.service.repository.service.IUserRepositoryService;
import com.example.florence.user.service.service.mapper.UserServiceMapper;
import it.florence.generate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepositoryService repositoryService;
    private UserServiceMapper mapper;

    @Autowired
    public UserServiceImpl(IUserRepositoryService repositoryService, UserServiceMapper mapper) {
        this.repositoryService = repositoryService;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        DBUser userToSave = mapper.mapperToDB(user);
        DBUser userSaved = repositoryService.save(userToSave);
        return mapper.mapperToWeb(userSaved);
    }

    @Override
    public User change(User user) {
        DBUser userToChange = repositoryService.findById(user.getId().intValue())
                .orElseThrow(() -> new UserServiceException("No user found to change", HttpStatus.BAD_REQUEST));
        DBUser userChanged = mapper.mapperChangeUserForDB(user, userToChange);
        return  mapper.mapperToWeb(repositoryService.change(userChanged));
    }

    @Override
    public void delete(User user) {
        DBUser userToDelete = mapper.mapperToDB(user);
        repositoryService.delete(userToDelete);
    }

    @Override
    public List<User> findAll() {
        return repositoryService.findAll()
                .stream()
                .map(data ->  mapper.mapperToWeb(data))
                .toList();
    }

    @Override
    public User findById(BigDecimal id) {
        return repositoryService.findById(id.intValue())
                .map(data ->  mapper.mapperToWeb(data))
                .orElse(null);
    }

    @Override
    public List<User> findBy(User user) {
        DBUser u = mapper.mapperToDB(user);
        return repositoryService.findBy(Example.of(u))
                .stream()
                .map(data -> mapper.mapperToWeb(data))
                .toList();
    }

}
