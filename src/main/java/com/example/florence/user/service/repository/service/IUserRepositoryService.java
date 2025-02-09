package com.example.florence.user.service.repository.service;

import com.example.florence.user.service.repository.model.DBUser;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

public interface IUserRepositoryService {
    DBUser save(DBUser user);
    DBUser change(DBUser user);
    void delete(DBUser user);
    List<DBUser> findAll();
    Optional<DBUser> findById(String id);
    List<DBUser> findBy(Example<DBUser> user);
}
