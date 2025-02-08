package com.example.florence_user_service.repository.service;

import com.example.florence_user_service.repository.connector.UserRepository;
import com.example.florence_user_service.repository.model.DBUser;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRepositoryServiceImpl implements IUserRepositoryService {

    private UserRepository userRepository;

    public UserRepositoryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public DBUser save(DBUser user) {
        return userRepository.save(user);
    }

    @Override
    public DBUser change(DBUser user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(DBUser user) {
        userRepository.delete(user);
    }

    @Override
    public List<DBUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<DBUser> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<DBUser> findBy(Example<DBUser> user) {
        return userRepository.findAll(user);
    }

}
